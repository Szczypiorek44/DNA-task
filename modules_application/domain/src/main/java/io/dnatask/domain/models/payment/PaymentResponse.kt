package io.dnatask.domain.models.payment

data class PaymentResponse(val transactionID: String, val status: PaymentStatus)
