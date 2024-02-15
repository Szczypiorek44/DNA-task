package io.dnatask.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dnatask.data.CardReaderApiImpl
import io.dnatask.data.PaymentApiClientImpl
import io.dnatask.data.PurchaseApiClientImpl
import io.dnatask.domain.api.CardReaderApi
import io.dnatask.domain.api.PaymentApiClient
import io.dnatask.domain.api.PurchaseApiClient

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providePaymentApiClient(): PaymentApiClient = PaymentApiClientImpl()

    @Provides
    fun providePurchaseApiClient(): PurchaseApiClient = PurchaseApiClientImpl()

    @Provides
    fun provideCardReaderApi(): CardReaderApi = CardReaderApiImpl()
}