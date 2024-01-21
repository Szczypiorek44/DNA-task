package io.dnatask.domain.usecases

import io.dnatask.common.Product

interface GetProductsUseCase {

    suspend operator fun invoke(): List<Product>
}