package com.expediaclon.backend.service

import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val bookingRepository: BookingRepository,
    private val bookingService: BookingService
) {

    fun createPaymentIntent(bookingId: String): String {

        val booking = bookingRepository.findById(bookingId.toLong())
            .orElseThrow { RuntimeException("Reservation not found") }

        if (booking.status != BookingStatus.PENDING) {
            throw RuntimeException("Only pending reservations can be paid for")
        }

        val amount = booking.totalPrice  // USD → centavos

        val params = PaymentIntentCreateParams.builder()
            .setAmount(amount.toLong())
            .setCurrency("usd")
            .putMetadata("bookingId", booking.id.toString())
            .build()

        val paymentIntent = PaymentIntent.create(params)

        bookingService.updateBookingStatus(bookingId.toLong(), BookingStatus.CONFIRMED)

        return paymentIntent.clientSecret
    }
}
