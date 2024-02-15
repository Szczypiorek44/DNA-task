package io.dnatask.domain.usecases

import io.dnatask.domain.api.PurchaseApiClient
import io.dnatask.domain.models.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class GetProductsUseCaseImpl @Inject constructor(
    private val purchaseApiClient: PurchaseApiClient,
    private val dispatcher: CoroutineDispatcher
) : GetProductsUseCase {

    override suspend fun invoke(): List<Product> = withContext(dispatcher) {
        purchaseApiClient.getProducts()
    }

}
