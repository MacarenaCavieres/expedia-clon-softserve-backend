package com.expediaclon.backend.service

import com.expediaclon.backend.dto.PaymentIntentResponse
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.stripe.model.PaymentIntent
import com.stripe.param.PaymentIntentCreateParams
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PaymentService(
    private val bookingRepository: BookingRepository,
    private val bookingService: BookingService
) {

    fun createPaymentIntent(bookingId: String): PaymentIntentResponse {

        val booking = bookingRepository.findById(bookingId.toLong())
            .orElseThrow { RuntimeException("Reservation not found") }

        if (booking.status != BookingStatus.PENDING) {
            throw RuntimeException("Only pending reservations can be paid")
        }

        val amount = booking.totalPrice.multiply(BigDecimal(100)).toLong()

        val params = PaymentIntentCreateParams.builder()
            .setAmount(amount)
            .setCurrency("usd")
            .addPaymentMethodType("card")
            .putMetadata("bookingId", booking.id.toString())
            .build()

        val paymentIntent = PaymentIntent.create(params)

        return PaymentIntentResponse(
            clientSecret = paymentIntent.clientSecret,
            amount = amount,
            currency = "usd"
        )
    }
}
