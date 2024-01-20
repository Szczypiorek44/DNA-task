package io.dnatask.domain.di

import io.dnatask.domain.ProductUseCases
import io.dnatask.domain.ProductUseCasesImpl
import org.koin.dsl.module

val domainModule = module {
    single<ProductUseCases> { ProductUseCasesImpl(get(), get(), get()) }
}