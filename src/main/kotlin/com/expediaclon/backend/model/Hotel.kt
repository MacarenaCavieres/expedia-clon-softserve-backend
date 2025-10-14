package com.expediaclon.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "hotels")
data class Hotel(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val city: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    val latitude: Double,
    val longitude: Double,

    @ElementCollection
    @CollectionTable(name = "hotel_images", joinColumns = [JoinColumn(name = "hotel_id")])
    @Column(name = "images")
    val images: MutableList<String> = mutableListOf(),

    @Column(nullable = false)
    val rating: Double,

    val comment: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", nullable = false)
    val destination: Destination,

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    val rooms: List<RoomType> = emptyList()
)