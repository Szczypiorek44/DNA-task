package io.dnatask.domain.usecases

import io.dnatask.domain.models.Product
import io.dnatask.domain.models.purchase.BuyProductResult

interface BuyProductsUseCase {
    suspend operator fun invoke(products: List<Product>): BuyProductResult
}