package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.service.HotelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Controlador REST para gestionar las operaciones de consulta de Hoteles.
 *
 * Expone los endpoints bajo la ruta base "/api/hotels" y se enfoca
 * principalmente en la búsqueda (GET /) y visualización de detalles (GET /{hotelId}).
 *
 * @property hotelService El servicio inyectado (vía constructor) que maneja
 * la lógica de negocio relacionada con los hoteles.
 */
@Tag(name = "Hotels", description = "Endpoints for searching and retrieving hotel details")
@RestController
@RequestMapping("/api/hotels")
class HotelController(
    private val hotelService: HotelService
) {

    /**
     * Endpoint para buscar hoteles basado en criterios de ciudad y capacidad.
     * HTTP Method: GET
     * Ruta: /api/hotels?city=...&passengerCount=...
     *
     * @param city El nombre de la ciudad para la búsqueda (parámetro de consulta).
     * @param passengerCount El número de huéspedes para filtrar la capacidad (parámetro de consulta).
     * @return [ResponseEntity] con una lista de [HotelCardDto] (vista resumida) y estado 200 (OK).
     */
    @Operation(
        summary = "Search hotels by city",
        description = "Returns a list of available hotels in a specific city for the given number of passengers."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Hotels retrieved successfully"),
            ApiResponse(responseCode = "400", description = "Invalid parameters") // Manejado por GlobalExceptionHandler
        ]
    )
    // @GetMapping mapea las peticiones HTTP GET a la ruta base del controlador ("/api/hotels").
    @GetMapping
    fun searchHotels(
        // @RequestParam extrae parámetros de la URL (ej: ?city=Paris&passengerCount=2).
        @RequestParam city: String,
        @RequestParam passengerCount: Int
    ): ResponseEntity<List<HotelCardDto>> {

        // 1. Delega la lógica de búsqueda y filtrado al servicio.
        val hotels = hotelService.searchHotels(city, passengerCount)

        // 2. Devuelve la lista de DTOs "tarjeta" con estado 200 (OK).
        return ResponseEntity.ok(hotels)
    }


    /**
     * Endpoint para obtener los detalles completos de un hotel específico por su ID.
     * HTTP Method: GET
     * Ruta: /api/hotels/{hotelId}
     *
     * @param hotelId El ID único del hotel a consultar (extraído de la variable de ruta).
     * @return [ResponseEntity] con [HotelDetailDto] (vista detallada) y estado 200 (OK).
     * Si no se encuentra, se espera que el servicio lance una excepción
     * que resulte en un 404 (NOT FOUND) a través del GlobalExceptionHandler.
     */
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
    // Mapea peticiones GET a una sub-ruta que incluye una variable.
    @GetMapping("/{hotelId}")
    fun getHotelDetails(
        // @PathVariable extrae el valor de la URL (ej: /api/hotels/5).
        @PathVariable hotelId: Long
    ): ResponseEntity<HotelDetailDto> {

        // 1. Delega la obtención de los detalles al servicio.
        // Si el ID no existe, el servicio debe lanzar una excepción
        // (ej. EntityNotFoundException) que será capturada por el GlobalExceptionHandler.
        val hotelDetails = hotelService.getHotelDetails(hotelId)

        // 2. Devuelve el DTO de "detalles" con estado 200 (OK).
        return ResponseEntity.ok(hotelDetails)
    }
}