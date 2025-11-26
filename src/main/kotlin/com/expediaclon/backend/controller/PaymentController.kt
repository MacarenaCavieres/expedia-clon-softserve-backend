package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.CreatePaymentRequest
import com.stripe.param.PaymentIntentCreateParams
import com.stripe.model.PaymentIntent
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/payments")
class PaymentController {

    @PostMapping("/create-payment-intent")
    fun createPaymentIntent(@RequestBody req: CreatePaymentRequest): ResponseEntity<Map<String, String>> {
        val params = PaymentIntentCreateParams.builder()
            .setAmount(req.amount) // e.g., 1000 = $10.00
            .setCurrency(req.currency)
            // .addPaymentMethodType("card") // opcional
            .build()

        val paymentIntent: PaymentIntent = PaymentIntent.create(params)

        val clientSecret = paymentIntent.clientSecret
        return ResponseEntity.ok(mapOf("clientSecret" to clientSecret))
    }
}