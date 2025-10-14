package com.expediaclon.backend.dto

import com.expediaclon.backend.model.enums.BookingStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

// Define los datos que esperamos recibir del frontend para crear una reserva.
data class BookingRequest(
    val roomId: Long,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalGuests: Int,
    val guestNames: String
)

// Define los datos que enviaremos de vuelta al frontend como confirmación.
data class BookingResponse(
    val id: Long,
    val hotelName: String,
    val passengerCount: Int,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalPrice: BigDecimal
)

data class UpdateStatusRequestDto(
    val status:BookingStatus
)
