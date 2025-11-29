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

    @PostMapping("/webhook", consumes = ["application/json"])
    fun handleStripeEvent(
        @RequestBody payload: String,
        @RequestHeader("Stripe-Signature") sigHeader: String
    ): String {

        val event = Webhook.constructEvent(
            payload,
            sigHeader,
            endpointSecret
        )

        println("Stripe webhook received: ${event.type}")

        if (event.type == "payment_intent.succeeded") {

            val jsonObject = com.google.gson.JsonParser
                .parseString(payload)
                .asJsonObject

            val intent = jsonObject
                .getAsJsonObject("data")
                .getAsJsonObject("object")

            println(intent)

            val metadata = intent.getAsJsonObject("metadata")
            val bookingId = metadata?.get("bookingId")?.asString

            println("RAW metadata = $metadata")
            println("bookingId = $bookingId")

            if (!bookingId.isNullOrBlank()) {
                bookingService.updateBookingStatusFromWebhook(
                    bookingId.toLong(),
                    BookingStatus.CONFIRMED
                )
                println("✅ Booking $bookingId CONFIRMED")
            } else {
                println("❌ bookingId missing in metadata")
            }
        }

        return "ok"
    }


}
