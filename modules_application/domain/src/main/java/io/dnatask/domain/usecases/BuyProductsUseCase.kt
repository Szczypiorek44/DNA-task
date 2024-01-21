package io.dnatask.domain.usecases

import io.dnatask.common.Product
import io.dnatask.domain.models.purchase.BuyProductResult

interface BuyProductsUseCase {
    suspend operator fun invoke(products: List<Product>): BuyProductResult
}