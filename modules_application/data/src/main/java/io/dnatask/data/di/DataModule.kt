package io.dnatask.data.di

import io.dnatask.data.CardReaderApiImpl
import io.dnatask.data.PaymentApiClientImpl
import io.dnatask.data.PurchaseApiClientImpl
import io.dnatask.domain.api.CardReaderApi
import io.dnatask.domain.api.PaymentApiClient
import io.dnatask.domain.api.PurchaseApiClient
import org.koin.dsl.module

val dataModule = module {
    single<CardReaderApi> { CardReaderApiImpl() }
    single<PaymentApiClient> { PaymentApiClientImpl() }
    single<PurchaseApiClient> { PurchaseApiClientImpl() }
}