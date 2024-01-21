package io.dnatask.domain.di

import io.dnatask.domain.usecases.BuyProductsUseCase
import io.dnatask.domain.usecases.BuyProductsUseCaseImpl
import io.dnatask.domain.usecases.GetProductsUseCase
import io.dnatask.domain.usecases.GetProductsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<GetProductsUseCase> { GetProductsUseCaseImpl(get()) }
    single<BuyProductsUseCase> { BuyProductsUseCaseImpl(get(), get(), get()) }
}