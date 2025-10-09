package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.GuestItineraryResponse
import com.expediaclon.backend.service.ItineraryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// @RestController combina @Controller y @ResponseBody, simplificando la creación de APIs REST.
@RestController
// @RequestMapping define la ruta base para todos los endpoints en este controlador.
@RequestMapping("/api/itineraries")
class ItineraryController(
    private val itineraryService: ItineraryService
) {
    // @PostMapping mapea las peticiones HTTP POST a este método.
    @PostMapping
    fun createGuestItinerary(): ResponseEntity<GuestItineraryResponse> {

        val itinerary = itineraryService.createGuestItinerary()

        // Creamos un DTO para la respuesta, exponiendo solo los datos necesarios.
        val response = GuestItineraryResponse(itinerary.id, itinerary.sessionId!!)

        // ResponseEntity nos permite controlar el código de estado, cabeceras y cuerpo de la respuesta.
        return ResponseEntity.ok(response)
    }
}