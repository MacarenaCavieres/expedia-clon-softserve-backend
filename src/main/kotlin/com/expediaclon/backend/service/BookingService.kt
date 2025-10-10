package com.expediaclon.backend.service

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequest
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.slf4j.LoggerFactory
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
        // [INFO] Se registra el inicio del proceso. Este mensaje siempre aparecerá.
        logger.info("Iniciando creación de reserva para la sesión: ${request.sessionId}")

        // [DEBUG] Se registran detalles internos que solo son útiles para depurar.
        logger.debug("Buscando RoomType con ID: ${request.roomTypeId}")

        val roomType = roomTypeRepository.findById(request.roomTypeId)
            .orElseThrow {
                // [ERROR] Se registra un fallo crítico si no se encuentra la habitación.
                logger.error("No se encontró el RoomType con ID: ${request.roomTypeId}")
                IllegalArgumentException("No se encontró el tipo de habitación.")
            }
        logger.debug("RoomType encontrado: ${roomType.name} en el hotel ${roomType.hotel.name}")

        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)

        if (numberOfNights <= 0) {
            throw IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.")
        }

        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))

        val confirmationCode = generateConfirmationCode()

        logger.debug("Noches calculadas: $numberOfNights, Precio Total: $totalPrice")

        val newBooking = Booking(
            sessionId = request.sessionId,
            passengerCount = request.passengerCount,
            roomType = roomType,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice,
            confirmationCode = confirmationCode,
            status = BookingStatus.CONFIRMED
        )

        val savedBooking = bookingRepository.save(newBooking)
        // [INFO] Se registra la finalización exitosa del proceso.
        logger.info("Reserva creada con éxito. Código de confirmación: ${savedBooking.confirmationCode}")

        return savedBooking
    }

    private fun generateConfirmationCode(): String {
        return UUID.randomUUID().toString().substring(0, 8).uppercase()
    }

    // Obtiene todas las reservas (sin autenticación por ahora).
    @Transactional(readOnly = true)
    fun getAllBookings(): List<BookingDetailDto> {
        logger.info("Obteniendo todas las reservas")
        return bookingRepository.findAll().map { it.toDetailDto() }
    }

    // Obtiene los detalles de una reserva específica por su ID.
    @Transactional(readOnly = true)
    fun getBookingDetails(bookingId: Long): BookingDetailDto {
        logger.info("Obteniendo detalles para la reserva con ID: $bookingId")
        val booking = bookingRepository.findById(bookingId)
            .orElseThrow { IllegalArgumentException("Reserva con ID $bookingId no encontrada.") }
        return booking.toDetailDto()
    }

    // Cambia el estado de una reserva a CANCELLED.
    @Transactional
    fun cancelBooking(bookingId: Long): BookingDetailDto {
        logger.info("Iniciando cancelación para la reserva con ID: $bookingId")
        val booking = bookingRepository.findById(bookingId)
            .orElseThrow { IllegalArgumentException("Reserva con ID $bookingId no encontrada.") }

        // Lógica de negocio para la cancelación.
        if (booking.status == BookingStatus.CANCELLED) {
            throw IllegalStateException("La reserva ya ha sido cancelada.")
        }

        booking.status = BookingStatus.CANCELLED
        val updatedBooking = bookingRepository.save(booking)
        logger.info("Reserva con ID $bookingId cancelada con éxito.")

        return updatedBooking.toDetailDto()
    }

    // Función de extensión para convertir una entidad Booking a su DTO.
    private fun com.expediaclon.backend.model.Booking.toDetailDto(): BookingDetailDto {
        return BookingDetailDto(
            id = this.id,
            confirmationCode = this.confirmationCode,
            hotelName = this.roomType?.hotel?.name ?: "N/A",
            roomName = this.roomType?.name ?: "N/A",
            passengerCount = this.passengerCount,
            checkInDate = this.checkInDate,
            checkOutDate = this.checkOutDate,
            totalPrice = this.totalPrice,
            status = this.status.name
        )
    }
}