package io.dnatask.domain.repositories

import io.dnatask.common.Product
import io.dnatask.domain.models.purchase.PurchaseCancelRequest
import io.dnatask.domain.models.purchase.PurchaseConfirmRequest
import io.dnatask.domain.models.purchase.PurchaseRequest
import io.dnatask.domain.models.purchase.PurchaseResponse
import io.dnatask.domain.models.purchase.PurchaseStatusResponse

interface PurchaseApiClient {
    suspend fun getProducts(): List<Product>

    suspend fun initiatePurchaseTransaction(purchaseRequest: PurchaseRequest): PurchaseResponse

    suspend fun confirm(purchaseRequest: PurchaseConfirmRequest): PurchaseStatusResponse

    suspend fun cancel(purchaseRequest: PurchaseCancelRequest): PurchaseStatusResponse
}