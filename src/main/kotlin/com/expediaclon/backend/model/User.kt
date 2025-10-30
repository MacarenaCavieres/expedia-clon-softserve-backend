package com.expediaclon.backend.model

import com.expediaclon.backend.model.enums.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

import java.time.Instant

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val lastname: String,

    @Column(nullable = false, unique = true)
    @field:Email(message = "Must be a valid email format")
    val email: String,

    @Column(nullable = false)
    val phone: String,

    @Column(nullable = false)
    @field:Size(min = 8, message = "The password must be at least 8 characters long")
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.USER,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val bookings: MutableSet<Booking> = mutableSetOf(),

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    var updatedAt: Instant = Instant.now()
)