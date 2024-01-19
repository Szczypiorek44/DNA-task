package io.dnatask.presentation.di

import io.dnatask.presentation.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { ProductsViewModel(get()) }
}