package com.expediaclon.backend.repository

import com.expediaclon.backend.model.RoomType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomTypeRepository : JpaRepository<RoomType, Long>