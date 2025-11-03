package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.*
import com.expediaclon.backend.service.BookingService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class BookingGraphQLController(
    private val bookingService: BookingService
) {

    @MutationMapping
    fun createBooking(@Argument input: BookingRequestDto): BookingResponseDto {
        return bookingService.createBooking(input)
    }

    @QueryMapping
    fun allBookingsByUserId(): List<BookingResponseDto> =
        bookingService.getAllBookingsByUser()

    @QueryMapping
    fun bookingById(@Argument id: Long): BookingResponseDto =
        bookingService.getBookingDetails(id)

    @MutationMapping
    fun updateBooking(@Argument id: Long, @Argument input: BookingRequestDto): BookingResponseDto =
        bookingService.updateBooking(id, input)


    @MutationMapping
    fun updateBookingStatus(@Argument input: UpdateStatusRequestDto): BookingResponseDto =
        bookingService.updateBookingStatus(input.bookingId, input.status)

    @MutationMapping
    fun deleteBooking(@Argument id: Long): Boolean =
        bookingService.deleteBooking(id)
}
