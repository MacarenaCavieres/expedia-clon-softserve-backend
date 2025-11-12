package com.expediaclon.backend.repository


import com.expediaclon.backend.model.Hotel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param // Necesitas importar Param
import org.springframework.stereotype.Repository

@Repository
interface HotelRepository : JpaRepository<Hotel, Long> {

    fun findByCity(city: String): List<Hotel>

    @Query(
        """
        SELECT DISTINCT h FROM Hotel h JOIN h.rooms rt
        WHERE LOWER(h.city) = LOWER(:city) AND rt.capacity >= :minCapacity
    """
    )
    fun findHotelsInCityWithSufficientCapacity(
        @Param("city") city: String,
        @Param("minCapacity") minCapacity: Int
    ): List<Hotel>
}