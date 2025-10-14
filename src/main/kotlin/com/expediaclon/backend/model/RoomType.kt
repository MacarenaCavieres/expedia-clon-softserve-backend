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
    @Id
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    val hotel: Hotel,

    val name: String,
    val capacity: Int,

    @Column(name = "bed_type")
    val bedType: String,

    @Column(name = "price_per_night")
    val pricePerNight: Double,

    @Column(name = "image_url")
    val imageUrl: String,

    val description:String

)