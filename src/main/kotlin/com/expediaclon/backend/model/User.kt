package com.expediaclon.backend.model

import com.expediaclon.backend.model.enums.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

import java.time.Instant

// @Entity le indica a JPA que esta clase es un modelo de una tabla de base de datos.
@Entity
// @Table especifica el nombre de la tabla.
@Table(name = "users")
data class User(
    // @Id marca este campo como la clave primaria.
    // @GeneratedValue le dice a la base de datos que genere este valor automáticamente.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    // @Column permite personalizar la columna. 'nullable = false' la hace obligatoria,
    // 'unique = true' asegura que no haya dos emails iguales.
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    // @Enumerated le dice a JPA cómo guardar el enum. 'STRING' lo guarda como texto (ej: "USER").
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.USER,

    @Column(nullable = false)
    val createdAt: Instant = Instant.now(),

    @Column(nullable = false)
    var updatedAt: Instant = Instant.now()
)