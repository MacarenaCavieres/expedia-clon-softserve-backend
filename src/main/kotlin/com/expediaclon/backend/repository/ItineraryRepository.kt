package com.expediaclon.backend.repository

import com.expediaclon.backend.model.Itinerary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ItineraryRepository : JpaRepository<Itinerary, Long> {
    // Spring Data JPA crea automáticamente la implementación de este método.
    // basándose en el nombre: buscará un itinerario por su campo 'sessionId'.
    fun findBySessionId(sessionId: UUID): Itinerary?
}