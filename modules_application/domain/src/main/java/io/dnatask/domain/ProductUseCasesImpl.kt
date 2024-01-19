package io.dnatask.domain

import android.util.Log
import io.dnatask.data.CardReaderService
import io.dnatask.data.PaymentApiClient
import io.dnatask.data.Product
import io.dnatask.data.PurchaseApiClient
import io.dnatask.data.models.PaymentRequest
import io.dnatask.data.models.PaymentStatus
import io.dnatask.data.models.PurchaseRequest
import io.dnatask.data.models.TransactionStatus
import io.dnatask.domain.models.BuyProductResult

class ProductUseCasesImpl(
    private val paymentApiClient: PaymentApiClient,
    private val purchaseApiClient: PurchaseApiClient,
    private val cardReaderService: CardReaderService
) : ProductUseCases {

    companion object {
        private const val TAG = "ProductUseCasesImpl"

        private const val NUMBER_OF_ITEMS: Long = 1
        private const val CURRENCY = "EUR"
    }

    override suspend fun getProducts(): List<Product> {
        return purchaseApiClient.getProducts()
    }

    override suspend fun buy(productID: String): BuyProductResult {
        Log.d(TAG, "buy(productID: $productID)")
        val purchaseResponse = purchaseApiClient.initiatePurchaseTransaction(
            PurchaseRequest(mapOf(productID to NUMBER_OF_ITEMS))
        )
        Log.d(TAG, "purchaseResponse: $purchaseResponse")

        purchaseResponse.transactionStatus.let {
            if (it == TransactionStatus.CANCELLED) {
                return BuyProductResult.Failed("Transaction has been cancelled")
            } else if (it == TransactionStatus.FAILED) {
                return BuyProductResult.Failed("Transaction has failed")
            }
        }

        val cardData = try {
            cardReaderService.readCard()
        } catch (throwable: Throwable) {
            return BuyProductResult.Failed(throwable.localizedMessage ?: "Unknown card read error")
        }
        Log.d(TAG, "cardData: $cardData")

        val paymentResponse = paymentApiClient.pay(
            PaymentRequest(purchaseResponse.transactionID, 10.0, CURRENCY, cardData.token)
        )
        Log.d(TAG, "paymentResponse: $paymentResponse")

        return paymentResponse.status.let {
            when (it) {
                PaymentStatus.SUCCESS -> {
                    BuyProductResult.Success
                }

                PaymentStatus.FAILED -> {
                    BuyProductResult.Failed("Failed to pay for transaction ${paymentResponse.transactionID}")
                }
            }
        }
    }
}