package com.expediaclon.backend.service

import com.expediaclon.backend.model.Itinerary
import com.expediaclon.backend.repository.ItineraryRepository
import org.springframework.stereotype.Service
import java.util.UUID

// @Service le indica a Spring que esta clase contiene lógica de negocio.
@Service
class ItineraryService(
    private val itineraryRepository: ItineraryRepository
) {
    // Lógica para crear un itinerario para un usuario no registrado.
    fun createGuestItinerary(): Itinerary {
        val newItinerary = Itinerary(
            sessionId = UUID.randomUUID()
        )
        return itineraryRepository.save(newItinerary)
    }
}