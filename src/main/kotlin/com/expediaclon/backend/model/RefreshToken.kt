package com.expediaclon.backend.model
import jakarta.persistence.*
import java.time.Instant


@Entity
@Table(name = "refresh_tokens")
data class RefreshToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "expires_at", nullable = false)
    val expiresAt: Instant,

    @Column(name = "hashed_token", nullable = false, unique = true)
    val hashedToken: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant = Instant.now()
)