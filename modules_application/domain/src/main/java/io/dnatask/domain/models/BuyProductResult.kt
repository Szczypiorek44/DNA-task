package io.dnatask.domain.models

sealed class BuyProductResult {
    object Success : BuyProductResult()
    class Failed(val errorMsg: String) : BuyProductResult()
}