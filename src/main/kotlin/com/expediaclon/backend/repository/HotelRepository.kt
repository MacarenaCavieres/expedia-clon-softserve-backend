package com.expediaclon.backend.repository


import com.expediaclon.backend.model.Hotel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param // Necesitas importar Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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

    @Modifying
    @Transactional
    @Query(
        value = """
        UPDATE hotels
        SET embedding = CAST(:embedding AS vector)
        WHERE id = :hotelId
        """,
        nativeQuery = true
    )
    fun updateEmbedding(
        @Param("hotelId") hotelId: Long,
        @Param("embedding") embedding: String
    )

    @Query(
        value = """
        SELECT *
        FROM hotels
        WHERE embedding IS NOT NULL
        ORDER BY embedding <-> CAST(:queryVector AS vector)
        LIMIT :limit
        """,
        nativeQuery = true
    )
    fun semanticSearch(
        @Param("queryVector") queryVector: String,
        @Param("limit") limit: Int
    ): List<Hotel>
}