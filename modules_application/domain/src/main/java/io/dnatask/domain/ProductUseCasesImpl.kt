package io.dnatask.domain

import android.util.Log
import io.dnatask.common.Product
import io.dnatask.data.CardReaderService
import io.dnatask.data.PaymentApiClient
import io.dnatask.data.PurchaseApiClient
import io.dnatask.data.models.payment.PaymentRequest
import io.dnatask.data.models.payment.PaymentStatus
import io.dnatask.data.models.purchase.PurchaseConfirmRequest
import io.dnatask.data.models.purchase.PurchaseRequest
import io.dnatask.data.models.purchase.PurchaseStatusResponse
import io.dnatask.data.models.transactionStatus.TransactionStatus
import io.dnatask.domain.models.BuyProductResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class ProductUseCasesImpl(
    private val paymentApiClient: PaymentApiClient,
    private val purchaseApiClient: PurchaseApiClient,
    private val cardReaderService: CardReaderService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductUseCases {

    companion object {
        private const val TAG = "ProductUseCasesImpl"

        private const val NUMBER_OF_ITEMS: Long = 1
        private const val CURRENCY = "EUR"
    }

    override suspend fun getProducts(): List<Product> = withContext(ioDispatcher) {
        purchaseApiClient.getProducts()
    }

    override suspend fun buy(productID: String): BuyProductResult = withContext(ioDispatcher) {
        Log.d(TAG, "buy(productID: $productID)")

        val purchaseResponse = purchaseApiClient.initiatePurchaseTransaction(
            PurchaseRequest(mapOf(productID to NUMBER_OF_ITEMS))
        )
        Log.d(TAG, "purchaseResponse: $purchaseResponse")

        purchaseResponse.transactionStatus.let {
            if (it == TransactionStatus.CANCELLED) {
                return@withContext BuyProductResult.Failed("Transaction has been cancelled")
            } else if (it == TransactionStatus.FAILED) {
                return@withContext BuyProductResult.Failed("Transaction has failed")
            }
        }

        val cardData = try {
            cardReaderService.readCard()
        } catch (throwable: Throwable) {
            return@withContext BuyProductResult.Failed(
                throwable.message ?: "Unknown card read error"
            )
        }
        Log.d(TAG, "cardData: $cardData")

        val paymentResponse = paymentApiClient.pay(
            PaymentRequest(
                purchaseResponse.transactionID,
                purchaseResponse.amount,
                CURRENCY,
                cardData.token
            )
        )
        Log.d(TAG, "paymentResponse: $paymentResponse")

        if (paymentResponse.status != PaymentStatus.SUCCESS) {
            return@withContext BuyProductResult.Failed("Failed to pay for transaction ${paymentResponse.transactionID}")
        }

        return@withContext purchaseApiClient
            .confirm(PurchaseConfirmRequest(purchaseResponse.order, purchaseResponse.transactionID))
            .toBuyProductResult()
    }

    private fun PurchaseStatusResponse.toBuyProductResult(): BuyProductResult {
        return when (status) {
            TransactionStatus.CONFIRMED -> {
                BuyProductResult.Success
            }

            else -> {
                BuyProductResult.Failed("Could not confirm transaction $transactionID. ConfirmationStatus: $status")
            }
        }
    }
}
