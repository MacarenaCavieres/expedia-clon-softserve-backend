package com.expediaclon.backend.repository

import com.expediaclon.backend.model.Hotel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

// @Repository le indica a Spring que esta interfaz es para acceder a datos.
// JpaRepository nos da métodos CRUD (Create, Read, Update, Delete) gratis.
@Repository
interface HotelRepository : JpaRepository<Hotel, Long> {
    fun findByCity(city: String): List<Hotel>
}