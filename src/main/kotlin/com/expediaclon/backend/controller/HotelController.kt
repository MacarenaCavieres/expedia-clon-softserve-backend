package com.expediaclon.backend.controller

import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.service.HotelService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hotels")
class HotelController(
    private val hotelService: HotelService
) {
    // @GetMapping mapea las peticiones HTTP GET.
    @GetMapping
    fun searchHotels(
        // @RequestParam extrae parámetros de la URL (ej: ?city=Paris&passengerCount=2).
        @RequestParam city: String,
        @RequestParam passengerCount: Int
    ): ResponseEntity<List<Hotel>> {
        val hotels = hotelService.searchHotels(city, passengerCount)
        return ResponseEntity.ok(hotels)
    }
}