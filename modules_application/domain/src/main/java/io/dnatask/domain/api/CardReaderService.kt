package io.dnatask.domain.api

import io.dnatask.domain.models.card.CardData

interface CardReaderService {

    suspend fun readCard(): CardData
}