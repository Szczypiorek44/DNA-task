package io.dnatask.domain.models.payment

data class PaymentRequest(
    val transactionID: String,
    val amount: Double,
    val currency: String,
    val cardToken: String
)



