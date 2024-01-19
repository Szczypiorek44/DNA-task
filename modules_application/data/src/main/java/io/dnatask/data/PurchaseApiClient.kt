package io.dnatask.data

import io.dnatask.common.Product
import io.dnatask.data.models.PurchaseCancelRequest
import io.dnatask.data.models.PurchaseConfirmRequest
import io.dnatask.data.models.PurchaseRequest
import io.dnatask.data.models.PurchaseResponse
import io.dnatask.data.models.PurchaseStatusResponse
import io.dnatask.data.models.TransactionStatus
import kotlinx.coroutines.delay
import java.util.UUID

class PurchaseApiClient {
    companion object {
        val productList = listOf(
            Product("12345", "Big soda", 123, 2.99, "EUR", 0.22),
            Product("12346", "Medium soda", 30, 1.95, "EUR", 0.22),
            Product("12347", "Small soda", 1000, 1.25, "EUR", 0.22),
            Product("12348", "Chips", 2000, 4.33, "EUR", 0.22),
            Product("12349", "Snack bar", 0, 10.99, "EUR", 0.23),
        )
    }

    suspend fun getProducts(): List<Product> {
        delay(300)
        return productList
    }

    suspend fun initiatePurchaseTransaction(purchaseRequest: PurchaseRequest): PurchaseResponse {
        delay(250)
        if (purchaseRequest.order.isEmpty()) {
            return PurchaseResponse(
                purchaseRequest.order,
                UUID.randomUUID().toString(),
                TransactionStatus.FAILED
            )
        }

        return try {
            val sum = purchaseRequest.order.map { entry ->
                val orderedProduct = productList.first { product -> product.productID == entry.key }
                if (entry.value <= 0)
                    throw Exception("Not allowed to order not positive number of items")

                if (entry.value > orderedProduct.maxAmount)
                    throw Exception("Not allowed to order more then maxAmount")

                entry.value * orderedProduct.unitNetValue * (1.0 + orderedProduct.tax)
            }.sum()

            PurchaseResponse(
                purchaseRequest.order,
                UUID.randomUUID().toString(),
                TransactionStatus.INITIATED,
                sum
            )
        } catch (e: Exception) {
            PurchaseResponse(
                purchaseRequest.order,
                UUID.randomUUID().toString(),
                TransactionStatus.FAILED
            )
        }
    }

    suspend fun confirm(purchaseRequest: PurchaseConfirmRequest): PurchaseStatusResponse {
        delay(250)
        if (purchaseRequest.order.isEmpty()) {
            return PurchaseStatusResponse(purchaseRequest.transactionID, TransactionStatus.FAILED)
        }

        return try {
            val sum = purchaseRequest.order.map { entry ->
                val orderedProduct = productList.first { product -> product.productID == entry.key }

                if (entry.value <= 0)
                    throw Exception("Not allowed to order not positive number of items")

                entry.value * orderedProduct.unitNetValue * (1.0 + orderedProduct.tax)
            }.sum()

            if (sum > 100.0) {
                return PurchaseStatusResponse(
                    purchaseRequest.transactionID,
                    TransactionStatus.FAILED
                )
            }

            return PurchaseStatusResponse(
                purchaseRequest.transactionID,
                TransactionStatus.CONFIRMED
            )
        } catch (e: Exception) {
            return PurchaseStatusResponse(purchaseRequest.transactionID, TransactionStatus.FAILED)
        }
    }

    suspend fun cancel(purchaseRequest: PurchaseCancelRequest): PurchaseStatusResponse {
        delay(250)
        return PurchaseStatusResponse(purchaseRequest.transactionID, TransactionStatus.CANCELLED)
    }

}