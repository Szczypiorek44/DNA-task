package io.dnatask.presentation.di

import io.dnatask.common.Product
import io.dnatask.domain.ProductUseCases
import io.dnatask.domain.models.purchase.BuyProductResult
import io.dnatask.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val previewModule = module {
    viewModel {
        ProductsViewModel(object : ProductUseCases {
            override suspend fun getProducts(): List<Product> {
                TODO("Not yet implemented")
            }

            override suspend fun buy(products: List<Product>): BuyProductResult {
                TODO("Not yet implemented")
            }
        })
    }
}