package com.expediaclon.backend.service

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequest
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val roomTypeRepository: RoomTypeRepository
) {
    // Obtenemos una instancia del logger para esta clase.
    // Esto es una convención estándar para poder registrar mensajes.
    private val logger = LoggerFactory.getLogger(BookingService::class.java)

    @Transactional
    fun createBooking(request: BookingRequest): Booking {

        val room = roomTypeRepository.findByIdOrNull(request.roomId)
            ?: throw NoSuchElementException("Room with ID ${request.roomId} not found.")

        val hotel = room.hotel
            ?: throw IllegalStateException("Room is not associated with any hotel.")

        val numNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numNights <= 0) {
            throw IllegalArgumentException("Check-out date must be after check-in date.")
        }
        val totalPrice = room.pricePerNight * numNights.toDouble()

        val newBooking = Booking(
            id = null,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalGuests = request.totalGuests,
            guestNames = request.guestNames,
            totalPrice = totalPrice,
            status = BookingStatus.PENDING,
            hotelName = hotel.name,
            hotelCity = hotel.city,
            hotelImage = hotel.images.firstOrNull() ?: ""
        )

        val savedBooking = bookingRepository.save(newBooking)

        return savedBooking
    }

    private fun generateConfirmationCode(): String {
        return UUID.randomUUID().toString().substring(0, 8).uppercase()
    }

    // Obtiene todas las reservas (sin autenticación por ahora).
    @Transactional(readOnly = true)
    fun getAllBookings(): List<BookingDetailDto> {
        logger.info("Looking for all reservations")
        return bookingRepository.findAll().map { it.toDetailDto() }
    }

    // Obtiene los detalles de una reserva específica por su ID.
    @Transactional(readOnly = true)
    fun getBookingDetails(bookingId: Long): BookingDetailDto {
        logger.info("Obtaining details for reservation with ID: $bookingId")
        val booking = bookingRepository.findById(bookingId)
            .orElseThrow { IllegalArgumentException("Reservation with ID: $bookingId not found.") }
        return booking.toDetailDto()
    }

    fun updateReservation(id: Long, requestDto: BookingRequest): Booking? {
        val reservationToUpdate = bookingRepository.findByIdOrNull(id) ?: return null

        val room = roomTypeRepository.findByIdOrNull(requestDto.roomId)
            ?: throw NoSuchElementException("Room with ID ${requestDto.roomId} not found.")

        val hotel = room.hotel
            ?: throw IllegalStateException("Room is not associated with any hotel.")

        val numNights = ChronoUnit.DAYS.between(requestDto.checkInDate, requestDto.checkOutDate)
        if (numNights <= 0) {
            throw IllegalArgumentException("Check-out date must be after check-in date.")
        }
        val newTotalPrice = room.pricePerNight * numNights.toDouble()

        val updatedReservation = reservationToUpdate.copy(
            id = id,
            checkInDate = requestDto.checkInDate,
            checkOutDate = requestDto.checkOutDate,
            totalGuests = requestDto.totalGuests,
            guestNames = requestDto.guestNames,
            totalPrice = newTotalPrice,
            status = reservationToUpdate.status,
            hotelName = hotel.name,
            hotelCity = hotel.city,
            hotelImage = hotel.images.firstOrNull() ?: ""
        )

        return bookingRepository.save(updatedReservation)
    }

    // Cambia el estado de una reserva a CANCELLED.
    @Transactional
    fun cancelBooking(bookingId: Long, status: BookingStatus): Booking? {
        val reservationToUpdate = bookingRepository.findByIdOrNull(bookingId) ?: return null
        val updatedReservation = reservationToUpdate.copy(
            id = reservationToUpdate.id,
            status = status,
        )

        return bookingRepository.save(updatedReservation)
    }

    fun deleteReservation(id: Long): Boolean {
        return if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    // Función de extensión para convertir una entidad Booking a su DTO.
    private fun com.expediaclon.backend.model.Booking.toDetailDto(): BookingDetailDto {
        return BookingDetailDto(
            id = this.id,
            hotelName = this.hotelName ?: "N/A",
            totalGuests = this.totalGuests,
            checkInDate = this.checkInDate,
            checkOutDate = this.checkOutDate,
            totalPrice = this.totalPrice,
            status = this.status,
            hotelCity = this.hotelCity,
            hotelImage = this.hotelImage
        )
    }
}