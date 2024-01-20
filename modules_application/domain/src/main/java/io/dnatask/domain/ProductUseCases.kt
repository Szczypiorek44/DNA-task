package io.dnatask.domain

import io.dnatask.common.Product
import io.dnatask.domain.models.purchase.BuyProductResult

interface ProductUseCases {

    suspend fun getProducts(): List<Product>
    suspend fun buy(productID: String): BuyProductResult
}