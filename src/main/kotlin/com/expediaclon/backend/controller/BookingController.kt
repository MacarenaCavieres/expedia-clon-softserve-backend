package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.BookingDetailDto
import com.expediaclon.backend.dto.BookingRequest
import com.expediaclon.backend.dto.BookingResponse
import com.expediaclon.backend.service.BookingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/bookings")
class BookingController(
    private val bookingService: BookingService
) {

    @PostMapping
    fun createBooking(@RequestBody request: BookingRequest): ResponseEntity<BookingResponse> {
        val createdBooking = bookingService.createBooking(request)

        val response = BookingResponse(
            id = createdBooking.id,
            confirmationCode = createdBooking.confirmationCode,
            hotelName = createdBooking.roomType!!.hotel.name,
            roomName = createdBooking.roomType!!.name,
            passengerCount = createdBooking.passengerCount,
            checkInDate = createdBooking.checkInDate,
            checkOutDate = createdBooking.checkOutDate,
            totalPrice = createdBooking.totalPrice
        )

        return ResponseEntity.ok(response)
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

    // Cancela una reserva.
    @PostMapping("/{bookingId}/cancel")
    fun cancelBooking(@PathVariable bookingId: Long): ResponseEntity<BookingDetailDto> {
        val cancelledBooking = bookingService.cancelBooking(bookingId)
        return ResponseEntity.ok(cancelledBooking)
    }
}