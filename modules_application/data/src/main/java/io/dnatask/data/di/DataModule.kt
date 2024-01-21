package io.dnatask.data.di

import io.dnatask.data.CardReaderServiceImpl
import io.dnatask.data.PaymentApiClientImpl
import io.dnatask.data.PurchaseApiClientImpl
import io.dnatask.domain.api.CardReaderService
import io.dnatask.domain.api.PaymentApiClient
import io.dnatask.domain.api.PurchaseApiClient
import org.koin.dsl.module

val dataModule = module {
    single<CardReaderService> { CardReaderServiceImpl() }
    single<PaymentApiClient> { PaymentApiClientImpl() }
    single<PurchaseApiClient> { PurchaseApiClientImpl() }
}