package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequest
import com.expediaclon.backend.dto.BookingResponse
import com.expediaclon.backend.dto.UpdateStatusRequestDto
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.service.BookingService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/bookings")
class BookingController(
    private val bookingService: BookingService
) {

    @PostMapping
    fun createBooking(@RequestBody request: BookingRequest): ResponseEntity<Booking> {
        try {
            val savedReservation = bookingService.createBooking(request)
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation)
        } catch (e: NoSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    // --- NUEVOS ENDPOINTS ---

    // Obtiene todas las reservas.
    @GetMapping
    fun getAllBookings(): ResponseEntity<List<BookingDetailDto>> {
        val bookings = bookingService.getAllBookings()
        return ResponseEntity.ok(bookings)
    }

    // Obtiene los detalles de una reserva específica.
    @GetMapping("/{bookingId}")
    fun getBookingDetails(@PathVariable bookingId: Long): ResponseEntity<BookingDetailDto> {
        val bookingDetails = bookingService.getBookingDetails(bookingId)
        return ResponseEntity.ok(bookingDetails)
    }

    // Actualizar una reserva
    @PutMapping("/{bookingId}")
    fun putReservation(
        @PathVariable bookingId: Long,
        @RequestBody requestDto: BookingRequest
    ): ResponseEntity<Booking> {
        try {
            val updatedReservation = bookingService.updateReservation(bookingId, requestDto)
            return ResponseEntity.ok(updatedReservation)
        } catch (e: NoSuchElementException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        } catch (e: IllegalArgumentException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    // Cancela una reserva.
    @PatchMapping("/{bookingId}/status")
    fun cancelBooking(
        @PathVariable bookingId: Long,
        @RequestBody request: UpdateStatusRequestDto
    ): ResponseEntity<Booking> {
        val cancelledBooking = bookingService.cancelBooking(bookingId, request.status)
        return if (cancelledBooking != null) {
            ResponseEntity.ok(cancelledBooking)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @DeleteMapping("/{bookingId}")
    fun deleteReservationById(@PathVariable bookingId: Long): ResponseEntity<Unit> {
        val wasDeleted = bookingService.deleteReservation(bookingId)

        return if (wasDeleted) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}