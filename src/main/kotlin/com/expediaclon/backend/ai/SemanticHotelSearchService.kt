package com.expediaclon.backend.ai

import com.expediaclon.backend.ai.LocalEmbeddingService
import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.repository.HotelRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class SemanticHotelSearchService(
    private val hotelRepository: HotelRepository,
    private val embeddingService: LocalEmbeddingService
) {

    fun search(query: String, limit: Int = 4): List<HotelCardDto> {
        val embedding = embeddingService.createEmbedding(query)
        val vectorString = embedding.joinToString(prefix = "[", postfix = "]")

        return hotelRepository.semanticSearch(vectorString, limit)
            .map { hotel ->
                HotelCardDto(
                    id = hotel.id,
                    name = hotel.name,
                    city = hotel.city,
                    rating = hotel.rating,
                    comment = hotel.comment,
                    pricePerNight = hotel.rooms.minOfOrNull { it.pricePerNight }
                        ?.toPlainString() ?: "0",
                    mainImage = hotel.images.first()
                )
            }
    }
}

private fun toCardDto(hotel: Hotel): HotelCardDto {
    val minPrice = hotel.rooms.minOfOrNull { it.pricePerNight } ?: BigDecimal.ZERO
    val mainImage = hotel.images.firstOrNull() ?: ""

    return HotelCardDto(
        id = hotel.id,
        name = hotel.name,
        rating = hotel.rating,
        comment = hotel.comment,
        pricePerNight = minPrice.toPlainString(),
        mainImage = mainImage,
        city = hotel.city
    )
}
