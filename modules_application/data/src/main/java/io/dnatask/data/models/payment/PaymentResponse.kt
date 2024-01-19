package io.dnatask.data.models.payment

data class PaymentResponse(val transactionID: String, val status: PaymentStatus)
