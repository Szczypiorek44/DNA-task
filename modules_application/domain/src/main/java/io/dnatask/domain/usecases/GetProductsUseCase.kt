package io.dnatask.domain.usecases

import io.dnatask.domain.models.Product

interface GetProductsUseCase {

    suspend operator fun invoke(): List<Product>
}