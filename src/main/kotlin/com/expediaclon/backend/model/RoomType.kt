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
    // Añadimos GeneratedValue para que la BD asigne el ID automáticamente.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // Relación muchos-a-uno con Hotel.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    val hotel: Hotel,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val capacity: Int,

    @Column(nullable = false) // Mantenemos bedType ya que el frontend lo espera
    val bedType: String,

    // Precio por noche, usando BigDecimal para precisión monetaria.
    // precision=10, scale=2 significa hasta 10 dígitos en total, con 2 decimales (ej: 12345678.99).
    @Column(nullable = false, precision = 10, scale = 2)
    val pricePerNight: BigDecimal,

    // Usamos columnDefinition = "TEXT" para permitir URLs o descripciones largas.
    @Column(nullable = false, columnDefinition = "TEXT")
    val imageUrl: String, // Mantenemos imageUrl ya que el frontend lo espera

    @Column(columnDefinition = "TEXT")
    val description:String,

    // Campo crucial añadido para la lógica de disponibilidad futura.
    @Column(nullable = false)
    val totalInventory: Int
)