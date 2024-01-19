package io.dnatask.data.models.payment

data class PaymentRequest(
    val transactionID: String,
    val amount: Double,
    val currency: String,
    val cardToken: String
)



