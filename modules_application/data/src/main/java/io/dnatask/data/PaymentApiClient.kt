package io.dnatask.data

import io.dnatask.data.models.payment.PaymentRequest
import io.dnatask.data.models.payment.PaymentResponse
import io.dnatask.data.models.payment.PaymentStatus
import kotlinx.coroutines.delay

class PaymentApiClient {
    /**
     * Call this method to execute payment on the account connected with provided card token
     */
    suspend fun pay(paymentRequest: PaymentRequest): PaymentResponse {
        delay(2500)

        return if (paymentRequest.currency == "EUR") {
            PaymentResponse(paymentRequest.transactionID, PaymentStatus.SUCCESS)
        } else {
            PaymentResponse(paymentRequest.transactionID, PaymentStatus.FAILED)
        }
    }

    /**
     * Method meant for reverting payment when transaction could not be completed by the merchant.
     */
    suspend fun revert(paymentRequest: PaymentRequest): PaymentResponse {
        delay(500)
        return if (paymentRequest.amount >= 1.00) {
            PaymentResponse(paymentRequest.transactionID, PaymentStatus.SUCCESS)
        } else {
            PaymentResponse(paymentRequest.transactionID, PaymentStatus.FAILED)
        }
    }
}