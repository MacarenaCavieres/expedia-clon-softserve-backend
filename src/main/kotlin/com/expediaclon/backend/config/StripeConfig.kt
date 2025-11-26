package com.expediaclon.backend.config

import com.stripe.Stripe
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import jakarta.annotation.PostConstruct

@Configuration
class StripeConfig {
    @Value("\${stripe.secret-key}")
    lateinit var secretKey: String

    @PostConstruct
    fun init() {
        Stripe.apiKey = secretKey
    }
}