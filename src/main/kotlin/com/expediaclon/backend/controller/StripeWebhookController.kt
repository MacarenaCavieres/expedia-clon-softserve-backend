package com.expediaclon.backend.controller

import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.service.BookingService
import com.stripe.model.Event
import com.stripe.model.PaymentIntent
import com.stripe.net.Webhook
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stripe")
class StripeWebhookController(
    private val bookingService: BookingService
) {

    @Value("\${stripe.webhook.secret}")
    private lateinit var endpointSecret: String

    @PostMapping("/webhook")
    fun handleStripeEvent(
        @RequestBody payload: String,
        @RequestHeader("Stripe-Signature") sigHeader: String
    ): String {

        val event: Event = Webhook.constructEvent(
            payload,
            sigHeader,
            endpointSecret
        )

        if (event.type == "payment_intent.succeeded") {
            val intent = event.dataObjectDeserializer.`object`.get() as PaymentIntent
            val bookingId = intent.metadata["bookingId"]

            bookingId?.let {
                bookingService.updateBookingStatus(it.toLong(), BookingStatus.PAID)
            }
        }

        return "ok"
    }
}
