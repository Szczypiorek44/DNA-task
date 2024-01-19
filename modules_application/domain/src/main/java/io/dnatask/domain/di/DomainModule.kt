package io.dnatask.domain.di

import io.dnatask.data.CardReaderService
import io.dnatask.data.PaymentApiClient
import io.dnatask.data.PurchaseApiClient
import io.dnatask.domain.ProductUseCases
import io.dnatask.domain.ProductUseCasesImpl
import org.koin.dsl.module

val domainModule = module {
    single<ProductUseCases> { ProductUseCasesImpl(get(), get(), get()) }

    single<CardReaderService> { CardReaderService() }
    single<PaymentApiClient> { PaymentApiClient() }
    single<PurchaseApiClient> { PurchaseApiClient() }
}