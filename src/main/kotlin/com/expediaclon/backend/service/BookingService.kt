package com.expediaclon.backend.service

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequestDto // Usa el DTO correcto
import com.expediaclon.backend.dto.BookingResponseDto
import com.expediaclon.backend.dto.UserDtoForBooking
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.User
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import com.expediaclon.backend.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.time.temporal.ChronoUnit
import java.util.UUID

/**
 * Capa de servicio para gestionar la lógica de negocio de las Reservas (Bookings).
 *
 * Se encarga de la creación, validación, actualización y consulta de reservas,
 * interactuando con [BookingRepository] y [RoomTypeRepository].
 *
 * @property bookingRepository Repositorio para operaciones CRUD de [Booking].
 * @property roomTypeRepository Repositorio para consultar datos de [RoomType] (ej. capacidad, precio).
 */
@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomTypeRepository: RoomTypeRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createBooking(request: BookingRequestDto): BookingResponseDto {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated")
        val userIdString = authentication.principal.toString()

        val userId = userIdString.toLongOrNull()
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user ID in context")

        val user = userRepository.findById(userId).orElseThrow {
            ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        }

        val passengerCount = request.passengerCount
        if (passengerCount <= 0) {
            throw IllegalArgumentException("The number of guests (totalGuests) must be greater than 0.")
        }

        val roomType = roomTypeRepository.findById(request.roomId)
            .orElseThrow {
                IllegalArgumentException("Room type not found.")
            }

        if (roomType.capacity < passengerCount) {
            throw IllegalArgumentException("The room capacity (${roomType.capacity}) is less than the number of guests ($passengerCount).")
        }

        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numberOfNights <= 0) {
            throw IllegalArgumentException("The departure date must be after the arrival date.")
        }
        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))

        val newBooking = Booking(
            passengerCount = passengerCount,
            guestNames = request.guestNames,
            roomType = roomType,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice,
            status = BookingStatus.PENDING,
            user = user
        )

        val savedBooking = bookingRepository.save(newBooking)

        val bookingEntity = bookingRepository.findById(savedBooking.id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Booking created but could not be retrieved.")
            }

        return mapToBookingResponseDto(bookingEntity)
    }

    @Transactional(readOnly = true)
    fun getAllBookingsByUser(): List<BookingResponseDto> {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated")
        val userIdString = authentication.principal.toString()

        val userId = userIdString.toLongOrNull()
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user ID in context")

        return bookingRepository.findBookingsByUserId(userId).map { mapToBookingResponseDto(it) }
    }

    @Transactional(readOnly = true)
    fun getBookingDetails(bookingId: Long): BookingResponseDto {
        val booking = findBookingByIdOrThrow(bookingId)
        return mapToBookingResponseDto(booking)
    }


    @Transactional
    fun updateBooking(bookingId: Long, request: BookingRequestDto): BookingResponseDto {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated")
        val userIdString = authentication.principal.toString()

        val userId = userIdString.toLongOrNull()
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user ID in context")

        val existingBooking = findBookingByIdOrThrow(bookingId)

        if (existingBooking.user.id != userId) {
            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "User is not authorized to update this booking."
            )
        }

        val passengerCount = request.passengerCount
        if (existingBooking.roomType.capacity < passengerCount) {
            throw IllegalArgumentException("The room capacity (${existingBooking.roomType.capacity}) is less than the number of guests ($passengerCount).")
        }

        val roomType = existingBooking.roomType

        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numberOfNights <= 0) {
            throw IllegalArgumentException("The departure date must be after the arrival date.")
        }
        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))

        val updatedBooking = existingBooking.copy(
            passengerCount = passengerCount,
            guestNames = request.guestNames,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice
        )

        val savedBooking = bookingRepository.save(updatedBooking)
        return mapToBookingResponseDto(savedBooking)
    }

    @Transactional
    fun updateBookingStatus(bookingId: Long, newStatus: BookingStatus): BookingResponseDto {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated")
        val userIdString = authentication.principal.toString()

        val userId = userIdString.toLongOrNull()
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user ID in context")

        val booking = findBookingByIdOrThrow(bookingId)

        if (booking.user.id != userId) {
            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "User is not authorized to update this booking."
            )
        }

        if (booking.status == newStatus) {
            return mapToBookingResponseDto(booking)
        }

        val updatedBooking = booking.copy(status = newStatus)
        val savedBooking = bookingRepository.save(updatedBooking)
        return mapToBookingResponseDto(savedBooking)
    }

    @Transactional
    fun deleteBooking(bookingId: Long): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated")
        val userIdString = authentication.principal.toString()

        val userId = userIdString.toLongOrNull()
            ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid user ID in context")

        val booking = findBookingByIdOrThrow(bookingId)

        if (booking.user.id != userId) {
            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "User is not authorized to update this booking."
            )
        }

        return run {
            bookingRepository.delete(booking)
            true
        }
    }

    // --- MÉTODOS PRIVADOS HELPER ---
    private fun findBookingByIdOrThrow(bookingId: Long): Booking {
        return bookingRepository.findById(bookingId)
            .orElseThrow {
                IllegalArgumentException("Reservation with ID $bookingId not found.")
            }
    }

    private fun mapToBookingResponseDto(booking: Booking): BookingResponseDto {
        val userDto = UserDtoForBooking(
            id = booking.user.id.toString(),
            email = booking.user.email,
            phone = booking.user.phone,
            name = booking.user.name,
            lastname = booking.user.lastname
        )

        val hotel = booking.roomType.hotel

        return BookingResponseDto(
            id = booking.id.toString(),
            passengerCount = booking.passengerCount,
            guestNames = booking.guestNames,
            checkInDate = booking.checkInDate.toString(),
            checkOutDate = booking.checkOutDate.toString(),
            totalPrice = booking.totalPrice.toDouble(),
            status = booking.status.name,
            hotelName = hotel.name,
            hotelCity = hotel.city,
            hotelImage = hotel.images.firstOrNull(),
            roomId = booking.roomType.id.toString(),
            user = userDto
        )
    }
}