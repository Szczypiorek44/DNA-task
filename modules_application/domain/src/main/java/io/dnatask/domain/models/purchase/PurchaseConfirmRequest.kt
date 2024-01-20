package io.dnatask.domain.models.purchase

data class PurchaseConfirmRequest(val order: Map<String, Long>, val transactionID: String)
