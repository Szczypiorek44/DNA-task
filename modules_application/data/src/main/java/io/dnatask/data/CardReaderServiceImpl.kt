package io.dnatask.data

import android.util.Log
import io.dnatask.domain.api.CardReaderService
import io.dnatask.domain.models.card.CardData
import io.dnatask.domain.models.card.CardReaderException
import kotlinx.coroutines.delay
import java.util.Calendar
import java.util.Calendar.SECOND
import java.util.UUID

class CardReaderServiceImpl : CardReaderService {

    companion object {
        private const val TAG = "CardReaderServiceImpl"
    }

    override suspend fun readCardSafely(): CardData? {
        return try {
            readCard().also {
                Log.d(TAG, "readCardSafely() cardData: $it")
            }
        } catch (exception: CardReaderException) {
            Log.d(TAG, "readCardSafely() Failed to read card: ${exception.message}")
            null
        }
    }

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




