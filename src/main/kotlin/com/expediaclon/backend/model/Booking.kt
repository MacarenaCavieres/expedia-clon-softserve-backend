package com.expediaclon.backend.model

import com.expediaclon.backend.model.enums.BookingStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "bookings")
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Este campo ahora identifica la sesión del usuario no registrado.
    @Column(nullable = false)
    val sessionId: UUID,

    // El número de pasajeros ahora pertenece directamente a la reserva.
    @Column(nullable = false)
    val passengerCount: Int,

    // Una reserva puede ser de un tipo de habitación.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    val roomType: RoomType? = null,

    @Column(nullable = false)
    val checkInDate: LocalDate,

    @Column(nullable = false)
    val checkOutDate: LocalDate,

    @Column(nullable = false, unique = true)
    val confirmationCode: String,

    @Column(nullable = false)
    val totalPrice: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: BookingStatus = BookingStatus.PENDING,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now()

    // En el futuro, una reserva podría ser de un vuelo.
    // @ManyToOne @JoinColumn(name = "flight_id") val flight: Flight? = null,
)