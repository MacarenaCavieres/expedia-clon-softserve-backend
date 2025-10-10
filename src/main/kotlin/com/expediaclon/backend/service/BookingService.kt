package com.expediaclon.backend.service

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
}