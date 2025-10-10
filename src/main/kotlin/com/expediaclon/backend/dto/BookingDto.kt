package com.expediaclon.backend.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

// Define los datos que esperamos recibir del frontend para crear una reserva.
data class BookingRequest(
    val sessionId: UUID,
    val passengerCount: Int,
    val roomTypeId: Long,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate
)

// Define los datos que enviaremos de vuelta al frontend como confirmación.
data class BookingResponse(
    val id: Long,
    val confirmationCode: String,
    val hotelName: String,
    val roomName: String,
    val passengerCount: Int,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalPrice: BigDecimal
)