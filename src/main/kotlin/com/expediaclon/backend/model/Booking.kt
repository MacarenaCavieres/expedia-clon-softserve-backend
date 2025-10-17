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
import jakarta.persistence.Table

import java.time.LocalDate


@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val checkInDate: LocalDate,
    val checkOutDate: LocalDate,
    val totalGuests: Int,
    val guestNames: String,
    val totalPrice: Double,

    @Enumerated(EnumType.STRING)
    val status: BookingStatus,

    val hotelName: String,
    val hotelCity: String,
    val hotelImage: String,

    @Column(name = "room_id", nullable = false)
    val roomId: Long

    // En el futuro, una reserva podría ser de un vuelo.
    // @ManyToOne @JoinColumn(name = "flight_id") val flight: Flight? = null,
)