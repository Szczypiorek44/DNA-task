package io.dnatask.domain.models.purchase

sealed class BuyProductResult {
    object Success : BuyProductResult()
    class Failed(val errorMsg: String) : BuyProductResult()
}