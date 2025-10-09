package com.expediaclon.backend.dto
//Los DTOs son clases simples para transferir datos entre el cliente y el servidor, evitando exponer las entidades de la base de datos.

import java.util.UUID

// Define la estructura de la respuesta que enviamos al crear un itinerario de invitado.
data class GuestItineraryResponse(
    val id: Long,
    val sessionId: UUID
)