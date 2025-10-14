package com.expediaclon.backend.dto

import com.expediaclon.backend.model.enums.BookingStatus
import java.math.BigDecimal
import java.time.LocalDate

// DTO para representar los detalles de un tipo de habitación.
data class RoomTypeDetailDto(
    val id: Long,
    val capacity: Int,
    val name:String,
    val bedType: String,
    val pricePerNight: Double,
    val imageUrl: String
)

// DTO para los detalles completos de un hotel, incluyendo sus habitaciones.
data class HotelDetailDto(
    val id: Long,
    val name: String,
    val rating: Double,
    val description: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val images: List<String>,
    val rooms: List<RoomTypeDetailDto>
)

// DTO para ver los detalles de una reserva existente.
data class BookingDetailDto(
    val id: Long?,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalGuests: Int,
    val totalPrice: Double,
    val status: BookingStatus,
    val hotelName: String,
    val hotelCity: String,
    val hotelImage: String
)

data class HotelCardDto(
    val id: Long,
    val name: String,
    val rating: Double,
    val pricePerNight: Double,
    val city: String,
    val mainImage: String
)
