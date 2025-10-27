package com.expediaclon.backend.repository


import com.expediaclon.backend.model.Hotel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param // Necesitas importar Param
import org.springframework.stereotype.Repository

@Repository
interface HotelRepository : JpaRepository<Hotel, Long> {

    // Método existente para buscar solo por ciudad (puede ser útil en el futuro).
    fun findByCity(city: String): List<Hotel>

    // --- MÉTODO AÑADIDO ---
    // Busca hoteles en una ciudad que tengan AL MENOS UNA habitación
    // con capacidad mayor o igual a la mínima requerida.
    // DISTINCT previene duplicados si un hotel tiene varias habitaciones que cumplen.
    @Query("""
        SELECT DISTINCT h FROM Hotel h JOIN h.rooms rt
        WHERE h.city = :city AND rt.capacity >= :minCapacity
    """)
    fun findHotelsInCityWithSufficientCapacity(
        @Param("city") city: String,
        @Param("minCapacity") minCapacity: Int
    ): List<Hotel>
}