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

    @QueryMapping
    fun allBookings(): List<BookingDetailDto> =
        bookingService.getAllBookings()

    @QueryMapping
    fun bookingById(@Argument id: Long): BookingDetailDto =
        bookingService.getBookingDetails(id)

    @MutationMapping
    fun createBooking(@Argument input: BookingRequestDto): BookingDetailDto {
        val created = bookingService.createBooking(input)
        return bookingService.getBookingDetails(created.id)
    }

    @MutationMapping
    fun updateBooking(@Argument id: Long, @Argument input: BookingRequestDto): BookingDetailDto =
        bookingService.updateBooking(id, input)


    @MutationMapping
    fun updateBookingStatus(@Argument input: UpdateStatusRequestDto): BookingDetailDto =
        bookingService.updateBookingStatus(input.bookingId, input.status)

    @MutationMapping
    fun deleteBooking(@Argument id: Long): Boolean =
        bookingService.deleteBooking(id)
}
