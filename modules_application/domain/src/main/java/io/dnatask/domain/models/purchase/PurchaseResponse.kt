package io.dnatask.domain.models.purchase

import io.dnatask.domain.models.transaction.TransactionStatus

data class PurchaseResponse(
    val order: Map<String, Long>,
    val transactionID: String,
    val transactionStatus: TransactionStatus,
    val amount: Double = 0.0
)