package io.dnatask.domain.usecases

import android.util.Log
import io.dnatask.common.Product
import io.dnatask.domain.api.CardReaderService
import io.dnatask.domain.api.PaymentApiClient
import io.dnatask.domain.api.PurchaseApiClient
import io.dnatask.domain.models.card.CardData
import io.dnatask.domain.models.card.CardReaderException
import io.dnatask.domain.models.payment.PaymentRequest
import io.dnatask.domain.models.payment.PaymentStatus
import io.dnatask.domain.models.purchase.BuyProductResult
import io.dnatask.domain.models.purchase.PurchaseConfirmRequest
import io.dnatask.domain.models.purchase.PurchaseRequest
import io.dnatask.domain.models.transaction.TransactionStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class BuyProductsUseCaseImpl(
    private val paymentApiClient: PaymentApiClient,
    private val purchaseApiClient: PurchaseApiClient,
    private val cardReaderService: CardReaderService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BuyProductsUseCase {

    companion object {
        private const val TAG = "BuyProductsUseCaseImpl"

        private const val NUMBER_OF_ITEMS: Long = 1
        private const val CURRENCY = "EUR"
    }

    override suspend fun invoke(products: List<Product>): BuyProductResult =
        withContext(ioDispatcher) {
            Log.d(TAG, "buy(products: $products)")
            val product = products.first()
            val order = mapOf(product.productID to NUMBER_OF_ITEMS)
            buy(order)
        }

    private suspend fun buy(order: Map<String, Long>): BuyProductResult {
        val purchaseResponse = purchaseApiClient.initiatePurchaseTransaction(PurchaseRequest(order))

        val trxStatus = purchaseResponse.transactionStatus
        if (trxStatus == TransactionStatus.CANCELLED || trxStatus == TransactionStatus.FAILED) {
            return BuyProductResult.Failed("Failed to initiate purchase")
        }

        val cardData = cardReaderService.readCardSafely()
            ?: return BuyProductResult.Failed("Card data is null")

        val trxID = purchaseResponse.transactionID
        val amount = purchaseResponse.amount
        val token = cardData.token

        val paymentResponse = paymentApiClient.pay(PaymentRequest(trxID, amount, CURRENCY, token))
        if (paymentResponse.status == PaymentStatus.FAILED) {
            return BuyProductResult.Failed("Failed to pay for transaction ${paymentResponse.transactionID}")
        }

        val confirmRequest = PurchaseConfirmRequest(order, trxID)

        purchaseApiClient.confirm(confirmRequest).let {
            if (it.status == TransactionStatus.CONFIRMED) {
                return BuyProductResult.Success
            } else {
                return BuyProductResult.Failed("Could not confirm transaction $trxID. ConfirmationStatus: ${it.status}")
            }
        }
    }

    private suspend fun CardReaderService.readCardSafely(): CardData? {
        return try {
            readCard().also {
                Log.d(TAG, "cardData: $it")
            }
        } catch (exception: CardReaderException) {
            Log.d(TAG, "Failed to read card: ${exception.message}")
            null
        }
    }
}
