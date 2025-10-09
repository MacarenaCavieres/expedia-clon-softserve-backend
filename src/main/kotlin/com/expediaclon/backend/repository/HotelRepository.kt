package com.expediaclon.backend.repository

import com.expediaclon.backend.model.Hotel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

// @Repository le indica a Spring que esta interfaz es para acceder a datos.
// JpaRepository nos da métodos CRUD (Create, Read, Update, Delete) gratis.
@Repository
interface HotelRepository : JpaRepository<Hotel, Long> {

    // @Query nos permite escribir consultas personalizadas usando JPQL (parecido a SQL pero con entidades).
    // Esta consulta busca hoteles en una ciudad que tengan al menos una habitación con capacidad suficiente.
    @Query("SELECT h FROM Hotel h JOIN RoomType rt ON h.id = rt.hotel.id WHERE h.city = :city AND rt.capacity >= :minCapacity GROUP BY h.id")
    fun findHotelsInCityWithSufficientCapacity(city: String, minCapacity: Int): List<Hotel>
}