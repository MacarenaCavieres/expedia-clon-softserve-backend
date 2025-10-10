package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.BookingRequest
import com.expediaclon.backend.dto.BookingResponse
import com.expediaclon.backend.service.BookingService
import org.springframework.http.ResponseEntity
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

        // Creamos una respuesta DTO limpia y actualizada.
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
}