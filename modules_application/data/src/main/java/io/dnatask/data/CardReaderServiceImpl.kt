package io.dnatask.data

import io.dnatask.domain.models.card.CardData
import io.dnatask.domain.models.card.CardReaderException
import io.dnatask.domain.repositories.CardReaderService
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Calendar.SECOND
import java.util.UUID

class CardReaderServiceImpl : CardReaderService {

    override suspend fun readCard(): CardData {
        val second = Calendar.getInstance().get(SECOND)

        if (second <= 5) {
            // User will need some time to use the card
            delay(4000)
            return CardData(UUID.randomUUID().toString())
        }

        throw CardReaderException()
    }
}




