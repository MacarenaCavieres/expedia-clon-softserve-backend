package com.expediaclon.backend.repository

import com.expediaclon.backend.model.Destination
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DestinationRepository : JpaRepository<Destination, Long> {
    fun findByName(name: String): Destination?
}