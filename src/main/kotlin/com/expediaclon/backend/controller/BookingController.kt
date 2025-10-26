package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequestDto // Usa el DTO correcto para la entrada
import com.expediaclon.backend.dto.BookingCreationResponse // Usa el DTO correcto para la respuesta de creación
import com.expediaclon.backend.dto.UpdateStatusRequestDto
import com.expediaclon.backend.service.BookingService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Controlador REST para gestionar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * relacionadas con las Reservas (Bookings) de hotel.
 *
 * Expone los endpoints bajo la ruta base "/api/bookings".
 * Utiliza anotaciones de Swagger (OpenAPI 3) para la documentación de la API.
 */
@Tag(name = "Bookings", description = "Endpoints for managing hotel bookings")
@RestController
@RequestMapping("/api/bookings")
class BookingController(
    /**
     * Inyección de dependencias (mediante el constructor) del [BookingService].
     * El controlador delega toda la lógica de negocio a esta capa de servicio.
     */
    private val bookingService: BookingService
) {

    /**
     * Endpoint para crear una nueva reserva.
     * HTTP Method: POST
     * Ruta: /api/bookings
     *
     * @param request El [BookingRequestDto] enviado en el cuerpo (body) de la solicitud JSON.
     * @return [ResponseEntity] con [BookingCreationResponse] (solo ID y código) y estado 201 (CREATED).
     */
    @Operation(summary = "Create a booking", description = "Creates a new hotel booking.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Booking created successfully"),
        ApiResponse(responseCode = "400", description = "Invalid booking data (e.g., room capacity exceeded, invalid dates)")
    ])
    @PostMapping
    fun createBooking(@RequestBody request: BookingRequestDto): ResponseEntity<BookingCreationResponse> { // <-- Usa BookingRequestDto

        // El GlobalExceptionHandler manejará las IllegalArgumentException (errores de validación) devolviendo 400

        // 1. Delega la lógica de creación al servicio
        val createdBooking = bookingService.createBooking(request)

        // 2. Prepara la respuesta específica para la creación (DTO más ligero)
        val response = BookingCreationResponse(
            id = createdBooking.id,
            confirmationCode = createdBooking.confirmationCode
        )

        // 3. Devuelve una respuesta HTTP 201 (CREATED) con el DTO de respuesta en el cuerpo.
        return ResponseEntity.status(HttpStatus.CREATED).body(response) // <-- Devuelve 201 Created
    }

    /**
     * Endpoint para obtener una lista de todas las reservas.
     * HTTP Method: GET
     * Ruta: /api/bookings
     *
     * @return [ResponseEntity] con una lista de [BookingDetailDto] y estado 200 (OK).
     */
    @Operation(summary = "Get all bookings", description = "Retrieves a list of all existing bookings.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")])
    @GetMapping
    fun getAllBookings(): ResponseEntity<List<BookingDetailDto>> {
        // 1. Obtiene la lista completa de DTOs desde el servicio
        val bookings = bookingService.getAllBookings()

        // 2. Devuelve la lista con estado 200 (OK)
        return ResponseEntity.ok(bookings)
    }

    /**
     * Endpoint para obtener los detalles de una reserva específica por su ID.
     * HTTP Method: GET
     * Ruta: /api/bookings/{bookingId}
     *
     * @param bookingId El ID de la reserva a consultar (extraído de la URL).
     * @return [ResponseEntity] con [BookingDetailDto] si se encuentra (200 OK),
     * o estado 404 (NOT FOUND) si no existe.
     */
    @Operation(summary = "Get booking details by ID", description = "Retrieves details of a specific booking.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Booking details retrieved successfully"),
        ApiResponse(responseCode = "404", description = "Booking not found for the given ID")
    ])
    @GetMapping("/{bookingId}")
    fun getBookingDetails(@PathVariable bookingId: Long): ResponseEntity<BookingDetailDto> {

        // Aunque el GlobalExceptionHandler podría capturar errores,
        // un manejo explícito aquí nos permite devolver un 404 (NOT FOUND)
        // en lugar de un 400 (BAD REQUEST) genérico si esa es la lógica del servicio.

        // Opcional: Manejo específico para devolver 404 en lugar de 400 genérico
        try {
            // 1. Intenta obtener los detalles
            val bookingDetails = bookingService.getBookingDetails(bookingId)

            // 2. Si se encuentra, devuelve 200 (OK) con los detalles
            return ResponseEntity.ok(bookingDetails)

        } catch (e: IllegalArgumentException) { // Asumiendo que el servicio lanza esto si no lo encuentra

            // 3. Si el servicio indica que no existe, devuelve 404
            return ResponseEntity.notFound().build()
        }
    }

    /**
     * Endpoint para actualizar completamente una reserva existente.
     * HTTP Method: PUT
     * Ruta: /api/bookings/{bookingId}
     *
     * @param bookingId El ID de la reserva a actualizar (extraído de la URL).
     * @param request El [BookingRequestDto] con *todos* los datos actualizados.
     * @return [ResponseEntity] con el [BookingDetailDto] actualizado y estado 200 (OK).
     */
    @Operation(summary = "Update a booking", description = "Updates details (guests, names, dates) of an existing booking.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Booking updated successfully"),
        ApiResponse(responseCode = "400", description = "Invalid data for update (e.g., capacity exceeded, invalid dates)"),
        ApiResponse(responseCode = "404", description = "Booking not found for the given ID")
    ])
    @PutMapping("/{bookingId}")
    fun updateBooking( // Renombrado de putReservation
        @PathVariable bookingId: Long,
        // Reutilizamos BookingRequestDto, asumiendo que el frontend envía todos los datos para actualizar
        @RequestBody request: BookingRequestDto
    ): ResponseEntity<BookingDetailDto> { // <-- Devuelve BookingDetailDto

        // Dejamos que el GlobalExceptionHandler maneje los errores (400 si datos inválidos, 404 si no existe)

        // 1. Delega la lógica de actualización al servicio
        val updatedBookingDto = bookingService.updateBooking(bookingId, request)

        // 2. Devuelve 200 (OK) con la entidad actualizada
        return ResponseEntity.ok(updatedBookingDto)
    }

    /**
     * Endpoint para actualizar parcialmente el estado de una reserva (ej. CANCELLED).
     * HTTP Method: PATCH (ideal para actualizaciones parciales)
     * Ruta: /api/bookings/{bookingId}/status
     *
     * @param bookingId El ID de la reserva a actualizar (extraído de la URL).
     * @param request DTO [UpdateStatusRequestDto] que contiene solo el nuevo estado.
     * @return [ResponseEntity] con el [BookingDetailDto] actualizado y estado 200 (OK).
     */
    @Operation(summary = "Update booking status (e.g., Cancel)", description = "Updates the status of a specific booking.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Booking status updated successfully"),
        ApiResponse(responseCode = "400", description = "Invalid status provided or transition not allowed"),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @PatchMapping("/{bookingId}/status") // Usa PATCH para estado
    fun updateBookingStatus( // Renombrado de cancelBooking para ser más general
        @PathVariable bookingId: Long,
        @RequestBody request: UpdateStatusRequestDto // DTO específico para estado
    ): ResponseEntity<BookingDetailDto> { // <-- Devuelve BookingDetailDto

        // Dejamos que el GlobalExceptionHandler maneje los errores (400, 404)

        // 1. Delega la lógica de actualización de estado al servicio
        val updatedBookingDto = bookingService.updateBookingStatus(bookingId, request.status)

        // 2. Devuelve 200 (OK) con la entidad actualizada
        return ResponseEntity.ok(updatedBookingDto)
    }

    /**
     * Endpoint para eliminar una reserva por su ID.
     * HTTP Method: DELETE
     * Ruta: /api/bookings/{bookingId}
     *
     * @param bookingId El ID de la reserva a eliminar (extraído de la URL).
     * @return [ResponseEntity] vacía con estado 204 (NO CONTENT) si se eliminó,
     * o 404 (NOT FOUND) si no existía.
     */
    @Operation(summary = "Delete a booking", description = "Deletes a specific booking by its ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @DeleteMapping("/{bookingId}")
    fun deleteBooking(@PathVariable bookingId: Long): ResponseEntity<Unit> { // Renombrado de deleteReservationById

        // 1. El servicio intenta eliminar y devuelve true si tuvo éxito, false si no se encontró
        val wasDeleted = bookingService.deleteBooking(bookingId)

        // 2. Comprueba el resultado para devolver el código HTTP correcto
        return if (wasDeleted) {
            // Éxito: Devuelve 204 (NO CONTENT). No se envía cuerpo.
            ResponseEntity.noContent().build()
        } else {
            // Falla (No encontrado): Devuelve 404 (NOT FOUND).
            ResponseEntity.notFound().build()
        }
    }
}