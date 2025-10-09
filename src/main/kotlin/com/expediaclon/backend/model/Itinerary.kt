package com.expediaclon.backend.model

import com.expediaclon.backend.model.enums.ItineraryStatus
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
import java.time.Instant
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "itineraries")
data class Itinerary(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Un itinerario puede o no pertenecer a un usuario (para invitados).
    // @ManyToOne define una relación "muchos-a-uno".
    // @JoinColumn especifica la columna de clave foránea.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User? = null,

    // Identificador único para las sesiones de invitado.
    @Column(unique = true)
    val sessionId: UUID? = null,

    @Column(nullable = false)
    var name: String? = "My Trip",

    var startDate: LocalDate? = null,

    var endDate: LocalDate? = null,

    @Column(nullable = false)
    var passengerCount: Int = 1,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ItineraryStatus = ItineraryStatus.PLANNING,

    val createdAt: Instant = Instant.now(),

    var updatedAt: Instant = Instant.now()
)