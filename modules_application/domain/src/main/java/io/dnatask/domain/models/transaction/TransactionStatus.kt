package io.dnatask.domain.models.transaction

enum class TransactionStatus {
    INITIATED,
    CONFIRMED,
    CANCELLED,
    FAILED
}