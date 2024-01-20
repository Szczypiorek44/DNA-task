package io.dnatask.domain.models.purchase

import io.dnatask.domain.models.transaction.TransactionStatus

data class PurchaseStatusResponse(val transactionID: String, val status: TransactionStatus)
