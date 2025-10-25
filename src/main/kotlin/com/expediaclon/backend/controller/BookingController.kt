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

@Tag(name = "Bookings", description = "Endpoints for managing hotel bookings")
@RestController
@RequestMapping("/api/bookings")
class BookingController(
    private val bookingService: BookingService
) {

    @Operation(summary = "Create a booking", description = "Creates a new hotel booking.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Booking created successfully"),
        ApiResponse(responseCode = "400", description = "Invalid booking data (e.g., room capacity exceeded, invalid dates)")
    ])
    @PostMapping
    fun createBooking(@RequestBody request: BookingRequestDto): ResponseEntity<BookingCreationResponse> { // <-- Usa BookingRequestDto
        // El GlobalExceptionHandler manejará las IllegalArgumentException devolviendo 400
        val createdBooking = bookingService.createBooking(request)
        val response = BookingCreationResponse(
            id = createdBooking.id,
            confirmationCode = createdBooking.confirmationCode
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(response) // <-- Devuelve 201 Created
    }

    @Operation(summary = "Get all bookings", description = "Retrieves a list of all existing bookings.")
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Bookings retrieved successfully")])
    @GetMapping
    fun getAllBookings(): ResponseEntity<List<BookingDetailDto>> {
        val bookings = bookingService.getAllBookings()
        return ResponseEntity.ok(bookings)
    }

    @Operation(summary = "Get booking details by ID", description = "Retrieves details of a specific booking.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Booking details retrieved successfully"),
        ApiResponse(responseCode = "404", description = "Booking not found for the given ID")
    ])
    @GetMapping("/{bookingId}")
    fun getBookingDetails(@PathVariable bookingId: Long): ResponseEntity<BookingDetailDto> {
        // Dejamos que el servicio lance excepción si no encuentra, y el handler la convierte a 400/404
        try { // Opcional: Manejo específico para devolver 404 en lugar de 400 genérico
            val bookingDetails = bookingService.getBookingDetails(bookingId)
            return ResponseEntity.ok(bookingDetails)
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.notFound().build()
        }
    }

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
        // Dejamos que el handler maneje los errores
        val updatedBookingDto = bookingService.updateBooking(bookingId, request)
        return ResponseEntity.ok(updatedBookingDto)
    }

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
        // Dejamos que el handler maneje los errores
        val updatedBookingDto = bookingService.updateBookingStatus(bookingId, request.status)
        return ResponseEntity.ok(updatedBookingDto)
    }

    @Operation(summary = "Delete a booking", description = "Deletes a specific booking by its ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Booking deleted successfully"),
        ApiResponse(responseCode = "404", description = "Booking not found")
    ])
    @DeleteMapping("/{bookingId}")
    fun deleteBooking(@PathVariable bookingId: Long): ResponseEntity<Unit> { // Renombrado de deleteReservationById
        val wasDeleted = bookingService.deleteBooking(bookingId)
        return if (wasDeleted) {
            ResponseEntity.noContent().build() // 204 No Content
        } else {
            ResponseEntity.notFound().build() // 404 Not Found
        }
    }
}