package io.dnatask.domain.usecases

import io.dnatask.common.Product
import io.dnatask.domain.api.PurchaseApiClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class GetProductsUseCaseImpl(
    private val purchaseApiClient: PurchaseApiClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetProductsUseCase {

    override suspend fun invoke(): List<Product> = withContext(ioDispatcher) {
        purchaseApiClient.getProducts()
    }

}
