package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.service.HotelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Hotels", description = "Endpoints for searching and retrieving hotel details")
@RestController
@RequestMapping("/api/hotels")
class HotelController(
    private val hotelService: HotelService
) {
    // @GetMapping mapea las peticiones HTTP GET.
    @Operation(
        summary = "Search hotels by city",
        description = "Returns a list of available hotels in a specific city for the given number of passengers."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Hotels retrieved successfully"),
            ApiResponse(responseCode = "400", description = "Invalid parameters")
        ]
    )
    @GetMapping
    fun searchHotels(
        // @RequestParam extrae parámetros de la URL (ej: ?city=Paris&passengerCount=2).
        @RequestParam city: String,
        @RequestParam passengerCount: Int
    ): ResponseEntity<List<HotelCardDto>> {
        val hotels = hotelService.findAllHotelsByCity(city, passengerCount)
        return ResponseEntity.ok(hotels)
    }


    // @PathVariable extrae el valor de la URL (ej: /api/hotels/5).
    @Operation(
        summary = "Get hotel details",
        description = "Retrieves detailed information of a specific hotel by its ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Hotel details retrieved successfully"),
            ApiResponse(responseCode = "404", description = "Hotel not found")
        ]
    )
    @GetMapping("/{hotelId}")
    fun getHotelDetails(@PathVariable hotelId: Long): ResponseEntity<HotelDetailDto> {
        val hotelDetails = hotelService.getHotelDetails(hotelId)
        return ResponseEntity.ok(hotelDetails)
    }
}