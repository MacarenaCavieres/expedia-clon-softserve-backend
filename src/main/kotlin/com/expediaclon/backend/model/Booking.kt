package com.expediaclon.backend.model

import com.expediaclon.backend.model.enums.BookingStatus
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal // Importación necesaria
import java.time.Instant // Importación necesaria
import java.time.LocalDate
import java.util.UUID // Importación necesaria

@Entity
@Table(name = "bookings")
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val passengerCount: Int,

    @Column(nullable = false)
    @field:NotBlank(message = "The names of the guests are required")
    val guestNames: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    val roomType: RoomType,

    @Column(nullable = false)
    @field:NotNull(message = "The check in date is required")
    val checkInDate: LocalDate,

    @Column(nullable = false)
    @field:NotNull(message = "The check out date is required")
    val checkOutDate: LocalDate,

    @Column(nullable = false, precision = 10, scale = 2)
    val totalPrice: BigDecimal,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: BookingStatus = BookingStatus.PENDING,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
)