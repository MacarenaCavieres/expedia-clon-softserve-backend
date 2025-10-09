package com.expediaclon.backend.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "room_types")
data class RoomType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Un tipo de habitación pertenece a un solo hotel.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    val hotel: Hotel,

    @Column(nullable = false)
    val name: String,

    @Lob // @Lob indica que este campo puede ser un texto largo.
    val description: String,

    @Column(nullable = false)
    val capacity: Int,

    @Column(nullable = false)
    val pricePerNight: BigDecimal,

    @Column(nullable = false)
    val totalInventory: Int
)