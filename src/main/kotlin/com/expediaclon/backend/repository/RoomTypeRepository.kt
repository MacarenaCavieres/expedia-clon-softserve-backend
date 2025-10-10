package com.expediaclon.backend.repository

import com.expediaclon.backend.model.RoomType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomTypeRepository : JpaRepository<RoomType, Long>{
    // Spring Data JPA creará automáticamente la consulta para buscar por el ID del hotel.
    fun findByHotelId(hotelId: Long): List<RoomType>
}