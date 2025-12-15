package com.expediaclon.backend.ai

import com.expediaclon.backend.model.Hotel
import org.springframework.stereotype.Service
import java.security.MessageDigest
import kotlin.math.sqrt

@Service
class LocalEmbeddingService {

    private val VECTOR_SIZE = 1536

    fun createEmbedding(text: String): List<Float> {
        val digest = MessageDigest.getInstance("SHA-256")
            .digest(text.lowercase().toByteArray())

        val vector = MutableList(VECTOR_SIZE) { 0f }

        digest.forEachIndexed { index, byte ->
            vector[index % VECTOR_SIZE] += (byte.toInt() and 0xff) / 255f
        }

        return vector
    }

    fun buildHotelEmbeddingText(hotel: Hotel): String {
        return """
            ${hotel.name}
            ${hotel.city}
            ${hotel.description}
            Rating ${hotel.rating}
        """.trimIndent()
    }
}

