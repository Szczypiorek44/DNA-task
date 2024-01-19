package io.dnatask.data.models.purchase

import io.dnatask.data.models.transactionStatus.TransactionStatus

data class PurchaseResponse(
    val order: Map<String, Long>,
    val transactionID: String,
    val transactionStatus: TransactionStatus,
    val amount: Double = 0.0
)