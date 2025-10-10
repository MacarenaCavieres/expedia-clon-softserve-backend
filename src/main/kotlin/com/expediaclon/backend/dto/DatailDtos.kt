package com.expediaclon.backend.dto

import java.math.BigDecimal

// DTO para representar los detalles de un tipo de habitación.
data class RoomTypeDetailDto(
    val id: Long,
    val name: String,
    val description: String,
    val capacity: Int,
    val pricePerNight: BigDecimal
)

// DTO para los detalles completos de un hotel, incluyendo sus habitaciones.
data class HotelDetailDto(
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val stars: Int,
    // La respuesta incluirá una lista con los detalles de cada habitación.
    val rooms: List<RoomTypeDetailDto>
)

// DTO para ver los detalles de una reserva existente.
data class BookingDetailDto(
    val id: Long,
    val confirmationCode: String,
    val hotelName: String,
    val roomName: String,
    val passengerCount: Int,
    val checkInDate: java.time.LocalDate,
    val checkOutDate: java.time.LocalDate,
    val totalPrice: BigDecimal,
    val status: String
)
