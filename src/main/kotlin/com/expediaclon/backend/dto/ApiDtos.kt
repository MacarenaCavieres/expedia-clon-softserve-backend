package com.expediaclon.backend.dto

import com.expediaclon.backend.model.enums.BookingStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

// --- DTOs para Hoteles ---

// Coincide con `hotelSearchedSchema` del frontend.
data class HotelCardDto(
    val id: Long,
    val name: String,
    val city: String,
    val mainImage: String, // URL de la imagen principal
    val pricePerNight: BigDecimal, // Precio mínimo por noche (BigDecimal)
    val rating: Double,
    val comment: String
)

// Coincide con `roomSchema` del frontend.
data class RoomTypeDetailDto(
    val id: Long,
    val capacity: Int,
    val name: String,
    val bedType: String,
    val pricePerNight: BigDecimal, // BigDecimal
    val imageUrl: String,
    val description: String // Añadido basado en RoomType.kt
)

// Coincide con `hotelDetailSchema` del frontend.
data class HotelDetailDto(
    val id: Long,
    val name: String,
    val rating: Double,
    val comment: String,
    val description: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val images: List<String>,
    val rooms: List<RoomTypeDetailDto> // Lista de habitaciones
)

// --- DTOs para Reservas (Bookings) ---

// Coincide con `createBookingPayload` + `sessionId`. Este es el que recibe el backend.
data class BookingRequestDto(
    val sessionId: UUID,       // Identificador de sesión para invitados
    // Usamos 'totalGuests' porque es lo que envía el frontend. El servicio lo mapeará a passengerCount.
    val passengerCount: Int,
    val roomId: Long,          // ID del RoomType a reservar (coincide con frontend)
    val checkInDate: LocalDate, // Coincide con frontend
    val checkOutDate: LocalDate, // Coincide con frontend
    val guestNames: String     // Coincide con frontend
    // passengerCount opcional eliminado para simplificar y usar totalGuests
)

// Coincide con `bookingSchema` del frontend. Este es el que devuelve el backend en GET /bookings y GET /bookings/{id}.
data class BookingDetailDto(
    val id: Long, // No nullable, coincide con la entidad
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val passengerCount: Int, // Mantenemos este nombre para coincidir con frontend
    val guestNames: String,
    val totalPrice: BigDecimal, // BigDecimal
    // Enviamos el nombre del enum como String, Zod en frontend espera String.
    val status: String,
    val hotelName: String,
    val hotelCity: String,
    val hotelImage: String,
    val roomId: Long
)

// Coincide con `CancelTripInfo` del frontend (para PATCH /status).
data class UpdateStatusRequestDto(
    val status: BookingStatus // Recibe el Enum directamente
)

// DTO específico para la respuesta de creación POST /bookings.
data class BookingCreationResponse(
    val id: Long,
    val confirmationCode: String
    // Puedes añadir más campos si el frontend los necesita inmediatamente después de crear
)
