package com.expediaclon.backend.service

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequestDto // Usa el DTO correcto
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.User
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
    private val roomTypeRepository: RoomTypeRepository
) {
    // Inicializa el logger para esta clase, permitiendo registrar eventos y errores.
    private val logger = LoggerFactory.getLogger(BookingService::class.java)

    /**
     * Crea una nueva reserva (Booking) basada en la solicitud.
     * Realiza validaciones de capacidad, fechas y calcula el precio total.
     *
     * @param request El DTO [BookingRequestDto] con los datos de la reserva.
     * @return La entidad [Booking] recién creada y guardada.
     * @throws IllegalArgumentException Si los datos son inválidos (huéspedes <= 0,
     * habitación no encontrada, capacidad excedida, fechas inválidas).
     */
    @Transactional
    fun createBooking(request: BookingRequestDto): Booking {

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

        val confirmationCode = generateConfirmationCode()

        val userCreator = User(
            id = 1,
            email = "creator@example.com",
            phone = "123456789",
            password = "hashed_password",
            name = "Test",
            lastname = "Creator"
        )

        val newBooking = Booking(
            passengerCount = passengerCount,
            guestNames = request.guestNames,
            roomType = roomType,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice,
            confirmationCode = confirmationCode,
            status = BookingStatus.PENDING,
            user = userCreator
        )

        val savedBooking = bookingRepository.save(newBooking)
        return savedBooking
    }

    /**
     * Obtiene una lista de todas las reservas existentes, mapeadas a [BookingDetailDto].
     *
     * @return Lista de [BookingDetailDto].
     */
    @Transactional(readOnly = true) // Transacción optimizada para solo lectura.
    fun getAllBookings(): List<BookingDetailDto> {
        return bookingRepository.findAll().map { mapToBookingDetailDto(it) }
    }

    /**
     * Obtiene los detalles de una reserva específica por su ID.
     *
     * @param bookingId El ID de la reserva a buscar.
     * @return El [BookingDetailDto] correspondiente.
     * @throws IllegalArgumentException Si la reserva no se encuentra.
     */
    @Transactional(readOnly = true)
    fun getBookingDetails(bookingId: Long): BookingDetailDto {
        val booking = findBookingByIdOrThrow(bookingId)
        return mapToBookingDetailDto(booking)
    }

    /**
     * Actualiza una reserva existente (fechas, huéspedes, nombres).
     * No permite cambiar el tipo de habitación (roomId).
     *
     * @param bookingId El ID de la reserva a actualizar.
     * @param request El DTO [BookingRequestDto] con los nuevos datos.
     * @return El [BookingDetailDto] de la reserva actualizada.
     * @throws IllegalArgumentException Si la reserva no se encuentra, la capacidad es excedida o las fechas son inválidas.
     */
    @Transactional
    fun updateBooking(bookingId: Long, request: BookingRequestDto): BookingDetailDto {
        val existingBooking = findBookingByIdOrThrow(bookingId)

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
        return mapToBookingDetailDto(savedBooking)
    }

    /**
     * Actualiza únicamente el estado de una reserva (ej. de CONFIRMED a CANCELLED).
     *
     * @param bookingId El ID de la reserva a modificar.
     * @param newStatus El nuevo [BookingStatus] a aplicar.
     * @return El [BookingDetailDto] de la reserva actualizada.
     * @throws IllegalArgumentException Si la reserva no se encuentra.
     */
    @Transactional
    fun updateBookingStatus(bookingId: Long, newStatus: BookingStatus): BookingDetailDto {
        val booking = findBookingByIdOrThrow(bookingId)
        if (booking.status == newStatus) {
            return mapToBookingDetailDto(booking)
        }

        val updatedBooking = booking.copy(status = newStatus)
        val savedBooking = bookingRepository.save(updatedBooking)
        return mapToBookingDetailDto(savedBooking)
    }

    /**
     * Elimina una reserva de la base de datos.
     *
     * @param bookingId El ID de la reserva a eliminar.
     * @return `true` si la reserva fue encontrada y eliminada, `false` si no se encontró.
     */
    @Transactional
    fun deleteBooking(bookingId: Long): Boolean {
        println("bookingID =====> $bookingId")
        val booking = bookingRepository.findByIdOrNull(bookingId)

        return if (booking != null) {
            bookingRepository.delete(booking)
            true
        } else {
            false
        }
    }

    // --- MÉTODOS PRIVADOS HELPER ---

    /**
     * Método helper (privado) para buscar una [Booking] por ID.
     * Centraliza la lógica de "buscar o fallar". (Principio DRY)
     *
     * @param bookingId ID de la reserva.
     * @return La [Booking] encontrada.
     * @throws IllegalArgumentException Si no se encuentra la reserva.
     */
    private fun findBookingByIdOrThrow(bookingId: Long): Booking {
        return bookingRepository.findById(bookingId)
            .orElseThrow {
                IllegalArgumentException("Reservation with ID $bookingId not found.")
            }
    }

    /**
     * Método helper (privado) para generar un código de confirmación único.
     *
     * @return Un String de 8 caracteres alfanuméricos en mayúsculas (ej. "A1B2C3D4").
     */
    private fun generateConfirmationCode(): String {
        return UUID.randomUUID().toString()
            .replace("-", "")
            .substring(0, 8)
            .uppercase()
    }

    /**
     * Método helper (privado) para mapear la entidad [Booking] al DTO [BookingDetailDto].
     *
     * @param booking La entidad [Booking] a mapear.
     * @return El DTO [BookingDetailDto] que espera el frontend.
     */
    private fun mapToBookingDetailDto(booking: Booking): BookingDetailDto {

        val roomType = booking.roomType
        val hotel = roomType.hotel

        return BookingDetailDto(
            id = booking.id,
            checkInDate = booking.checkInDate,
            checkOutDate = booking.checkOutDate,
            passengerCount = booking.passengerCount,
            guestNames = booking.guestNames,
            totalPrice = booking.totalPrice,
            status = booking.status.name,
            hotelName = hotel.name,
            hotelCity = hotel.city,
            hotelImage = hotel.images.firstOrNull() ?: "",
            roomId = roomType.id
        )
    }
}