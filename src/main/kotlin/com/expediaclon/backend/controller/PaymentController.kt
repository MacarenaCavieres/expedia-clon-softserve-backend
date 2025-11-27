package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.CreatePaymentRequest
import com.expediaclon.backend.service.PaymentService
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/api/payments")
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping("/create-payment-intent")
    fun createPaymentIntent(
        @RequestBody req: CreatePaymentRequest
    ): ResponseEntity<Map<String, String>> {
        val clientSecret = paymentService.createPaymentIntent(req.bookingId)
        return ResponseEntity.ok(
            mapOf("clientSecret" to clientSecret)
        )
    }
}
