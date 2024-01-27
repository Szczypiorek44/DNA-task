package io.dnatask.domain.api

import io.dnatask.domain.models.card.CardData

interface CardReaderApi {

    suspend fun readCardSafely(): CardData?
    suspend fun readCard(): CardData
}