package com.expediaclon.backend.service

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequestDto // Usa el DTO correcto
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
    private val logger = LoggerFactory.getLogger(BookingService::class.java)

    @Transactional
    fun createBooking(request: BookingRequestDto): Booking { // <-- Usa BookingRequestDto
        logger.info("Iniciando creación de reserva para la sesión: ${request.sessionId}")

        // Usamos totalGuests que viene del frontend
        val passengerCount = request.totalGuests
        if (passengerCount <= 0) {
            throw IllegalArgumentException("El número de huéspedes (totalGuests) debe ser mayor que 0.")
        }

        logger.debug("Buscando RoomType con ID: ${request.roomId}")
        val roomType = roomTypeRepository.findById(request.roomId)
            .orElseThrow {
                logger.error("No se encontró el RoomType con ID: ${request.roomId}")
                IllegalArgumentException("No se encontró el tipo de habitación.")
            }
        logger.debug("RoomType encontrado: ${roomType.name} en Hotel ID: ${roomType.hotel.id}")

        if (roomType.capacity < passengerCount) {
            logger.warn("Intento de reserva fallido: Capacidad ${roomType.capacity} < Pasajeros ${passengerCount} para RoomType ID ${request.roomId}")
            throw IllegalArgumentException("La capacidad de la habitación (${roomType.capacity}) es menor que el número de huéspedes ($passengerCount).")
        }

        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numberOfNights <= 0) {
            throw IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.")
        }
        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))
        val confirmationCode = generateConfirmationCode()

        logger.debug("Noches: $numberOfNights, Pasajeros: $passengerCount, Precio Total: $totalPrice")

        val newBooking = Booking(
            sessionId = request.sessionId,
            passengerCount = passengerCount, // Usamos la variable validada
            guestNames = request.guestNames,
            roomType = roomType,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice,
            confirmationCode = confirmationCode,
            status = BookingStatus.CONFIRMED // Estado inicial
        )

        val savedBooking = bookingRepository.save(newBooking)
        logger.info("Reserva creada con éxito. ID: ${savedBooking.id}, Código: ${savedBooking.confirmationCode}")

        return savedBooking // Devolvemos la entidad completa
    }

    @Transactional(readOnly = true)
    fun getAllBookings(): List<BookingDetailDto> {
        logger.info("Obteniendo todas las reservas")
        return bookingRepository.findAll().map { mapToBookingDetailDto(it) }
    }

    @Transactional(readOnly = true)
    fun getBookingDetails(bookingId: Long): BookingDetailDto {
        logger.info("Obteniendo detalles para la reserva ID: $bookingId")
        val booking = findBookingByIdOrThrow(bookingId)
        return mapToBookingDetailDto(booking)
    }

    @Transactional
    fun updateBooking(bookingId: Long, request: BookingRequestDto): BookingDetailDto { // <-- Usa BookingRequestDto
        logger.info("Actualizando reserva ID: $bookingId")
        val existingBooking = findBookingByIdOrThrow(bookingId)

        val passengerCount = request.totalGuests // Usamos totalGuests del request
        if (existingBooking.roomType.capacity < passengerCount) {
            throw IllegalArgumentException("La capacidad de la habitación (${existingBooking.roomType.capacity}) es menor que el número de huéspedes ($passengerCount).")
        }

        // Asumimos que roomId NO cambia en la actualización según el frontend
        val roomType = existingBooking.roomType
        if (request.roomId != roomType.id) {
            logger.warn("Intento de cambiar RoomType durante actualización no soportado para Booking ID $bookingId. Se mantiene RoomType ID: ${roomType.id}")
            // Lanzar excepción si no se permite cambiar habitación:
            // throw IllegalArgumentException("No se permite cambiar el tipo de habitación en una actualización.")
        }

        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numberOfNights <= 0) {
            throw IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.")
        }
        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))

        val updatedBooking = existingBooking.copy(
            passengerCount = passengerCount,
            guestNames = request.guestNames,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice
            // sessionId, roomType, confirmationCode, status, createdAt no se modifican aquí
        )

        val savedBooking = bookingRepository.save(updatedBooking)
        logger.info("Reserva ID $bookingId actualizada.")
        return mapToBookingDetailDto(savedBooking)
    }

    @Transactional
    fun updateBookingStatus(bookingId: Long, newStatus: BookingStatus): BookingDetailDto {
        logger.info("Actualizando estado de reserva ID $bookingId a $newStatus")
        val booking = findBookingByIdOrThrow(bookingId)

        if (booking.status == newStatus) {
            logger.warn("La reserva ID $bookingId ya está en estado $newStatus.")
            return mapToBookingDetailDto(booking)
        }

        val updatedBooking = booking.copy(status = newStatus)
        val savedBooking = bookingRepository.save(updatedBooking)
        logger.info("Estado de reserva ID $bookingId actualizado a $newStatus.")
        return mapToBookingDetailDto(savedBooking)
    }

    @Transactional
    fun deleteBooking(bookingId: Long): Boolean {
        logger.info("Intentando eliminar reserva ID: $bookingId")
        val booking = bookingRepository.findByIdOrNull(bookingId)
        return if (booking != null) {
            bookingRepository.delete(booking)
            logger.info("Reserva ID $bookingId eliminada.")
            true
        } else {
            logger.warn("No se encontró reserva ID $bookingId para eliminar.")
            false
        }
    }

    private fun findBookingByIdOrThrow(bookingId: Long): Booking {
        return bookingRepository.findById(bookingId)
            .orElseThrow {
                logger.error("Reserva con ID $bookingId no encontrada.")
                IllegalArgumentException("Reserva con ID $bookingId no encontrada.")
            }
    }

    private fun generateConfirmationCode(): String {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).uppercase()
    }

    // Mapea la entidad Booking al DTO que espera el frontend.
    private fun mapToBookingDetailDto(booking: Booking): BookingDetailDto {
        // Acceso a relaciones LAZY debe estar en transacción (@Transactional en los métodos públicos lo asegura)
        val roomType = booking.roomType
        val hotel = roomType.hotel

        return BookingDetailDto(
            id = booking.id,
            checkInDate = booking.checkInDate,
            checkOutDate = booking.checkOutDate,
            totalGuests = booking.passengerCount, // Mapea passengerCount a totalGuests para el frontend
            guestNames = booking.guestNames,
            totalPrice = booking.totalPrice,
            status = booking.status.name, // Envía el nombre del enum como String
            hotelName = hotel.name,
            hotelCity = hotel.city,
            hotelImage = hotel.images.firstOrNull() ?: "",
            roomId = roomType.id
        )
    }
}