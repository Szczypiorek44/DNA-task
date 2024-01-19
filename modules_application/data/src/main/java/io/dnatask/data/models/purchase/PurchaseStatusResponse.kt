package io.dnatask.data.models.purchase

import io.dnatask.data.models.transactionStatus.TransactionStatus

data class PurchaseStatusResponse(val transactionID: String, val status: TransactionStatus)
