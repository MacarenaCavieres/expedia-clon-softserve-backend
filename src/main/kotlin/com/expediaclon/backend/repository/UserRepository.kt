package com.expediaclon.backend.repository


import com.expediaclon.backend.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:  JpaRepository<User, Long> {
}