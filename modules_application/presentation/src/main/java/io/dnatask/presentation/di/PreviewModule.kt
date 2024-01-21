package io.dnatask.presentation.di

import io.dnatask.common.Product
import io.dnatask.domain.models.purchase.BuyProductResult
import io.dnatask.domain.usecases.BuyProductsUseCase
import io.dnatask.domain.usecases.GetProductsUseCase
import io.dnatask.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val previewModule = module {
    viewModel {
        ProductsViewModel(
            object : GetProductsUseCase {
                override suspend fun invoke(): List<Product> {
                    TODO("Not yet implemented")
                }
            }, object : BuyProductsUseCase {
                override suspend fun invoke(products: List<Product>): BuyProductResult {
                    TODO("Not yet implemented")
                }

            })
    }
}