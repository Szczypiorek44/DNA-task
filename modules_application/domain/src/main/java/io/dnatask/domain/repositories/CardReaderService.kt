package io.dnatask.domain.repositories

import io.dnatask.domain.models.card.CardData

interface CardReaderService {

    suspend fun readCard(): CardData
}