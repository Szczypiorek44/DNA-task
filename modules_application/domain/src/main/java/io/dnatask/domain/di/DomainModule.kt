package io.dnatask.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.dnatask.domain.api.CardReaderApi
import io.dnatask.domain.api.PaymentApiClient
import io.dnatask.domain.api.PurchaseApiClient
import io.dnatask.domain.usecases.BuyProductsUseCase
import io.dnatask.domain.usecases.BuyProductsUseCaseImpl
import io.dnatask.domain.usecases.GetProductsUseCase
import io.dnatask.domain.usecases.GetProductsUseCaseImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideBuyProductsUseCase(
        paymentApiClient: PaymentApiClient,
        purchaseApiClient: PurchaseApiClient,
        cardReaderApi: CardReaderApi,
        dispatcher: CoroutineDispatcher
    ): BuyProductsUseCase =
        BuyProductsUseCaseImpl(paymentApiClient, purchaseApiClient, cardReaderApi, dispatcher)

    @Provides
    fun provideGetProductsUseCase(
        purchaseApiClient: PurchaseApiClient,
        dispatcher: CoroutineDispatcher
    ): GetProductsUseCase = GetProductsUseCaseImpl(purchaseApiClient, dispatcher)
}