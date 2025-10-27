package com.expediaclon.backend.model

import com.expediaclon.backend.model.enums.BookingStatus
import jakarta.persistence.*
import java.math.BigDecimal // Importación necesaria
import java.time.Instant // Importación necesaria
import java.time.LocalDate
import java.util.UUID // Importación necesaria

@Entity
@Table(name = "bookings")
data class Booking(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0, // Es mejor usar Long no nullable y dejar 0 como valor por defecto

    // Identificador de la sesión del usuario invitado.
    @Column(nullable = false)
    val sessionId: UUID,

    // Número de pasajeros para esta reserva específica.
    @Column(nullable = false)
    val passengerCount: Int,

    // Nombres de los huéspedes (separados por coma o similar).
    @Column(nullable = false)
    val guestNames: String,

    // Relación muchos-a-uno con el tipo de habitación reservado.
    // FetchType.LAZY es importante para el rendimiento.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false) // Define la columna FK
    val roomType: RoomType, // Relación con la entidad RoomType

    // Relación futura con vuelo (opcional)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "flight_id")
    // val flight: Flight? = null,

    @Column(nullable = false)
    val checkInDate: LocalDate,

    @Column(nullable = false)
    val checkOutDate: LocalDate,

    // Código único generado para la confirmación.
    @Column(nullable = false, unique = true)
    val confirmationCode: String,

    // Precio total calculado, usando BigDecimal para precisión.
    @Column(nullable = false, precision = 10, scale = 2)
    val totalPrice: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: BookingStatus = BookingStatus.PENDING, // Valor por defecto PENDING

    @Column(nullable = false, updatable = false) // No actualizable
    val createdAt: Instant = Instant.now()

    // Eliminados: totalGuests (redundante), hotelName, hotelCity, hotelImage, roomId (reemplazado por roomType)
)