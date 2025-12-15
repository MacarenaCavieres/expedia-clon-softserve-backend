package com.expediaclon.backend.dto

import com.expediaclon.backend.model.enums.BookingStatus
import java.math.BigDecimal
import java.time.LocalDate

// --- DTOs para Hoteles ---
data class HotelCardDto(
    val id: Long,
    val name: String,
    val city: String,
    val mainImage: String,
    val pricePerNight: String,
    val rating: Double,
    val comment: String
)

data class RoomTypeDetailDto(
    val id: Long,
    val capacity: Int,
    val name: String,
    val bedType: String,
    val pricePerNight: BigDecimal,
    val imageUrl: String,
    val description: String
)

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
    val rooms: List<RoomTypeDetailDto>
)

// --- DTOs para Reservas (Bookings) ---
data class BookingRequestDto(
    val passengerCount: Int,
    val roomId: Long,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val guestNames: String
)

data class BookingDetailDto(
    val id: Long,
    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val passengerCount: Int,
    val guestNames: String,
    val totalPrice: BigDecimal,
    val status: String,
    val hotelName: String,
    val hotelCity: String,
    val hotelImage: String,
    val roomId: Long
)

data class UpdateStatusRequestDto(
    val bookingId: Long,
    val status: BookingStatus
)

data class UserDtoForBooking(
    val id: String,
    val email: String,
    val phone: String,
    val name: String,
    val lastname: String
)

data class BookingResponseDto(
    val id: String,
    val passengerCount: Int,
    val guestNames: String,
    val checkInDate: String,
    val checkOutDate: String,
    val totalPrice: Double,
    val status: String,
    val hotelName: String?,
    val hotelCity: String?,
    val hotelImage: String?,
    val roomId: String,
    val user: UserDtoForBooking
)

// --- DTOs para Usuarios (Users) ---
data class UserRequestDto(
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val password: String
)

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDtoForBooking
)

data class PasswordResetRequest(
    val token: String,
    val newPassword: String
)

data class UserRequestUpdateDto(
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String
)

// payment
data class CreatePaymentRequest(
    val bookingId: String
)

data class PaymentIntentResponse(
    val clientSecret: String,
    val amount: Long,
    val currency: String
)

// AI
data class EmbeddingResponse(
    val embedding: Embedding
)

data class Embedding(
    val values: List<Float>
)