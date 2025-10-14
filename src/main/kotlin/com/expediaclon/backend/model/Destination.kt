package com.expediaclon.backend.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Destination(
    @Id
    val id: Long = 0,
    val name: String
)
