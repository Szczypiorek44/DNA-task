package io.dnatask.data

import io.dnatask.domain.models.payment.PaymentRequest
import io.dnatask.domain.models.payment.PaymentStatus
import io.dnatask.domain.models.purchase.PurchaseConfirmRequest
import io.dnatask.domain.models.purchase.PurchaseRequest
import io.dnatask.domain.models.transaction.TransactionStatus
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PaymentAPITests {
    private val paymentAPI = PaymentApiClientImpl()

    @Test
    fun whenCorrectDataThenSuccess() = runBlocking {
        // given
        val paymentRequest = PaymentRequest(
            transactionID = "Tr1",
            amount = 33.66,
            currency = "EUR",
            cardToken = "Token"
        )

        // when
        val paymentResponse = paymentAPI.pay(paymentRequest)

        // then
        assertEquals(paymentResponse.status, PaymentStatus.SUCCESS)
    }

    @Test
    fun whenIncorrectAmountThenFail() = runBlocking {
        // given
        val paymentRequest = PaymentRequest(
            transactionID = "Tr1",
            amount = 19.66,
            currency = "EUR",
            cardToken = "Token"
        )

        // when
        val paymentResponse = paymentAPI.pay(paymentRequest)

        // then
        assertEquals(paymentResponse.status, PaymentStatus.FAILED)
    }

    @Test
    fun whenIncorrectCurrencyThenFail() = runBlocking {
        // given
        val paymentRequest = PaymentRequest(
            transactionID = "Tr1",
            amount = 22.66,
            currency = "PLN",
            cardToken = "Token"
        )

        // when
        val paymentResponse = paymentAPI.pay(paymentRequest)

        // then
        assertEquals(paymentResponse.status, PaymentStatus.FAILED)
    }

    @Test
    fun whenRevertingCorrectAmountThenSuccess() = runBlocking {
        // given
        val paymentRequest = PaymentRequest(
            transactionID = "Tr1",
            amount = 12.66,
            currency = "EUR",
            cardToken = "Token"
        )

        // when
        val paymentResponse = paymentAPI.revert(paymentRequest)

        // then
        assertEquals(paymentResponse.status, PaymentStatus.SUCCESS)
    }

    @Test
    fun whenRevertingIncorrectAmountThenFail() = runBlocking {
        // given
        val paymentRequest = PaymentRequest(
            transactionID = "Tr1",
            amount = 0.66,
            currency = "EUR",
            cardToken = "Token"
        )

        // when
        val paymentResponse = paymentAPI.revert(paymentRequest)

        // then
        assertEquals(paymentResponse.status, PaymentStatus.FAILED)
    }
}

class PurchaseAPITests {
    private val purchaseApiClientImpl = PurchaseApiClientImpl()

    @Test
    fun whenGetProductsThenSuccess() = runBlocking {
        // when
        val products = purchaseApiClientImpl.getProducts()

        // then
        assertEquals(products.size, 5)
    }

    @Test
    fun whenInitiateEmptyOrderThenFail() = runBlocking {
        // given
        val purchaseRequest = PurchaseRequest(
            mapOf()
        )

        // when
        val purchaseResponse = purchaseApiClientImpl.initiatePurchaseTransaction(purchaseRequest)

        // then
        assertEquals(purchaseResponse.transactionStatus, TransactionStatus.FAILED)
    }

    @Test
    fun whenInitiateOrderWithZeroItemsThenFail() = runBlocking {
        // given
        val purchaseRequest = PurchaseRequest(
            mapOf("12345" to 0)
        )

        // when
        val purchaseResponse = purchaseApiClientImpl.initiatePurchaseTransaction(purchaseRequest)

        // then
        assertEquals(purchaseResponse.transactionStatus, TransactionStatus.FAILED)
    }

    @Test
    fun whenInitiateOrderWithToManyItemsThenFail() = runBlocking {
        // given
        val purchaseRequest = PurchaseRequest(
            mapOf("12348" to 2001)
        )

        // when
        val purchaseResponse = purchaseApiClientImpl.initiatePurchaseTransaction(purchaseRequest)

        // then
        assertEquals(purchaseResponse.transactionStatus, TransactionStatus.FAILED)
    }

    @Test
    fun whenConfirmrderWithToManyItemsThenFail() = runBlocking {
        // given
        val purchaseRequest = PurchaseConfirmRequest(
            mapOf("12348" to 2001),
            "Tr2"
        )

        // when
        val purchaseResponse = purchaseApiClientImpl.confirm(purchaseRequest)

        // then
        assertEquals(purchaseResponse.status, TransactionStatus.FAILED)
    }
}