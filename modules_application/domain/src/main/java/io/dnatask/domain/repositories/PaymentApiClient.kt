package io.dnatask.domain.repositories

import io.dnatask.domain.models.payment.PaymentRequest
import io.dnatask.domain.models.payment.PaymentResponse

interface PaymentApiClient {
    suspend fun pay(paymentRequest: PaymentRequest): PaymentResponse

    suspend fun revert(paymentRequest: PaymentRequest): PaymentResponse
}