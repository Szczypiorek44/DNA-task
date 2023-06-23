package io.dnatechnology.dnataskandroid.ui.api.data

data class PaymentRequest(val transactionUID: String, val amount: Double, val currency: String, val cardToken: String)

data class PaymentResponse(val transactionID: String, val status: PaymentStatus)

enum class PaymentStatus {
    SUCCESS,
    FAILED
}
