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
    @Transactional // Anotación clave: Inicia una transacción de base de datos (lectura/escritura).
    fun createBooking(request: BookingRequestDto): Booking { // <-- Usa BookingRequestDto
        logger.info("Iniciando creación de reserva para la sesión: ${request.sessionId}")

        // 1. Validación de Huéspedes (usamos totalGuests que viene del frontend)
        val passengerCount = request.passengerCount
        if (passengerCount <= 0) {
            logger.warn("Intento de reserva con totalGuests <= 0")
            throw IllegalArgumentException("El número de huéspedes (totalGuests) debe ser mayor que 0.")
        }

        // 2. Validación del Tipo de Habitación (RoomType)
        logger.debug("Buscando RoomType con ID: ${request.roomId}")
        val roomType = roomTypeRepository.findById(request.roomId)
            .orElseThrow {
                // Si no se encuentra, lanza excepción que será manejada por el GlobalExceptionHandler (-> 404)
                logger.error("No se encontró el RoomType con ID: ${request.roomId}")
                IllegalArgumentException("No se encontró el tipo de habitación.")
            }
        logger.debug("RoomType encontrado: ${roomType.name} en Hotel ID: ${roomType.hotel.id}")

        // 3. Validación de Capacidad
        if (roomType.capacity < passengerCount) {
            logger.warn("Intento de reserva fallido: Capacidad ${roomType.capacity} < Pasajeros ${passengerCount} para RoomType ID ${request.roomId}")
            throw IllegalArgumentException("La capacidad de la habitación (${roomType.capacity}) es menor que el número de huéspedes ($passengerCount).")
        }

        // 4. Cálculo de Noches y Precio Total
        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numberOfNights <= 0) {
            logger.warn("Intento de reserva con fechas inválidas (noches <= 0)")
            throw IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.")
        }
        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))

        // 5. Generación de Código de Confirmación
        val confirmationCode = generateConfirmationCode()

        logger.debug("Noches: $numberOfNights, Pasajeros: $passengerCount, Precio Total: $totalPrice")

        // 6. Creación de la Entidad Booking
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

        // 7. Guardado en Base de Datos
        val savedBooking = bookingRepository.save(newBooking)
        logger.info("Reserva creada con éxito. ID: ${savedBooking.id}, Código: ${savedBooking.confirmationCode}")

        return savedBooking // Devolvemos la entidad completa al controlador
    }

    /**
     * Obtiene una lista de todas las reservas existentes, mapeadas a [BookingDetailDto].
     *
     * @return Lista de [BookingDetailDto].
     */
    @Transactional(readOnly = true) // Transacción optimizada para solo lectura.
    fun getAllBookings(): List<BookingDetailDto> {
        logger.info("Obteniendo todas las reservas")
        // .findAll() obtiene todas las entidades y .map() las transforma una por una usando el helper.
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
        logger.info("Obteniendo detalles para la reserva ID: $bookingId")
        // Usa el helper 'findBookingByIdOrThrow' para evitar duplicar código de búsqueda/error
        val booking = findBookingByIdOrThrow(bookingId)
        // Mapea la entidad encontrada al DTO
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
    fun updateBooking(bookingId: Long, request: BookingRequestDto): BookingDetailDto { // <-- Usa BookingRequestDto
        logger.info("Actualizando reserva ID: $bookingId")

        // 1. Busca la reserva existente o lanza excepción
        val existingBooking = findBookingByIdOrThrow(bookingId)

        // 2. Valida la capacidad con el nuevo número de huéspedes
        val passengerCount = request.passengerCount // Usamos totalGuests del request
        if (existingBooking.roomType.capacity < passengerCount) {
            throw IllegalArgumentException("La capacidad de la habitación (${existingBooking.roomType.capacity}) es menor que el número de huéspedes ($passengerCount).")
        }

        // 3. Asumimos que roomId NO cambia en la actualización según el frontend
        val roomType = existingBooking.roomType
        if (request.roomId != roomType.id) {
            // Esta es una decisión de negocio: ¿se permite cambiar de habitación?
            logger.warn("Intento de cambiar RoomType durante actualización no soportado para Booking ID $bookingId. Se mantiene RoomType ID: ${roomType.id}")
            // Si no se permite, se podría lanzar una excepción:
            // throw IllegalArgumentException("No se permite cambiar el tipo de habitación en una actualización.")
        }

        // 4. Recalcula noches y precio total con las nuevas fechas
        val numberOfNights = ChronoUnit.DAYS.between(request.checkInDate, request.checkOutDate)
        if (numberOfNights <= 0) {
            throw IllegalArgumentException("La fecha de salida debe ser posterior a la fecha de entrada.")
        }
        val totalPrice = roomType.pricePerNight.multiply(BigDecimal.valueOf(numberOfNights))

        // 5. Crea la entidad actualizada usando .copy() (inmutabilidad)
        val updatedBooking = existingBooking.copy(
            passengerCount = passengerCount,
            guestNames = request.guestNames,
            checkInDate = request.checkInDate,
            checkOutDate = request.checkOutDate,
            totalPrice = totalPrice
            // Campos como sessionId, roomType, confirmationCode, status, createdAt no se modifican aquí
        )

        // 6. Guarda los cambios y mapea a DTO
        val savedBooking = bookingRepository.save(updatedBooking)
        logger.info("Reserva ID $bookingId actualizada.")
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
        logger.info("Actualizando estado de reserva ID $bookingId a $newStatus")

        // 1. Busca la reserva
        val booking = findBookingByIdOrThrow(bookingId)

        // 2. (Opcional) Evita una escritura innecesaria si el estado ya es el deseado
        if (booking.status == newStatus) {
            logger.warn("La reserva ID $bookingId ya está en estado $newStatus.")
            return mapToBookingDetailDto(booking) // Devuelve el estado actual sin guardar
        }

        // 3. Actualiza el estado usando .copy() y guarda
        val updatedBooking = booking.copy(status = newStatus)
        val savedBooking = bookingRepository.save(updatedBooking)
        logger.info("Estado de reserva ID $bookingId actualizado a $newStatus.")
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
        logger.info("Intentando eliminar reserva ID: $bookingId")

        // Usamos findByIdOrNull para manejar el caso de "no encontrado" manualmente
        val booking = bookingRepository.findByIdOrNull(bookingId)

        return if (booking != null) {
            // Si se encontró, se elimina
            bookingRepository.delete(booking)
            logger.info("Reserva ID $bookingId eliminada.")
            true
        } else {
            // Si no se encontró, se informa y devuelve false
            logger.warn("No se encontró reserva ID $bookingId para eliminar.")
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
                // Esta excepción será capturada por el GlobalExceptionHandler (-> 404)
                logger.error("Reserva con ID $bookingId no encontrada.")
                IllegalArgumentException("Reserva con ID $bookingId no encontrada.")
            }
    }

    /**
     * Método helper (privado) para generar un código de confirmación único.
     *
     * @return Un String de 8 caracteres alfanuméricos en mayúsculas (ej. "A1B2C3D4").
     */
    private fun generateConfirmationCode(): String {
        return UUID.randomUUID().toString() // "a1b2c3d4-..."
            .replace("-", "")          // "a1b2c3d4..."
            .substring(0, 8)          // "a1b2c3d4"
            .uppercase()              // "A1B2C3D4"
    }

    /**
     * Método helper (privado) para mapear la entidad [Booking] al DTO [BookingDetailDto].
     *
     * @param booking La entidad [Booking] a mapear.
     * @return El DTO [BookingDetailDto] que espera el frontend.
     */
    private fun mapToBookingDetailDto(booking: Booking): BookingDetailDto {

        // --- ¡Importante! ---
        // El acceso a 'booking.roomType' y 'roomType.hotel' funciona aquí
        // porque son relaciones LAZY (cargadas perezosamente).
        // Se cargan automáticamente porque este método *siempre* es llamado
        // desde otros métodos públicos que están anotados con @Transactional,
        // asegurando que la sesión de la base de datos sigue activa.

        val roomType = booking.roomType
        val hotel = roomType.hotel

        return BookingDetailDto(
            id = booking.id,
            checkInDate = booking.checkInDate,
            checkOutDate = booking.checkOutDate,
            passengerCount = booking.passengerCount, // Mapea passengerCount a totalGuests para el frontend
            guestNames = booking.guestNames,
            totalPrice = booking.totalPrice,
            status = booking.status.name, // Envía el nombre del enum como String (ej. "CONFIRMED")
            hotelName = hotel.name,
            hotelCity = hotel.city,
            hotelImage = hotel.images.firstOrNull() ?: "", // Toma la primera imagen o string vacío
            roomId = roomType.id
        )
    }
}