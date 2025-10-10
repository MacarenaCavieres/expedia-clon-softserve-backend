package com.expediaclon.backend.service

import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.dto.RoomTypeDetailDto
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelService(
    private val hotelRepository: HotelRepository,
    // Necesitamos el repositorio de habitaciones para obtener los detalles.
    private val roomTypeRepository: RoomTypeRepository
) {

    // Lógica para buscar hoteles (sin cambios).
    fun searchHotels(city: String, passengerCount: Int): List<Hotel> {
        return hotelRepository.findHotelsInCityWithSufficientCapacity(city, passengerCount)
    }

    // Busca un hotel por su ID y lo transforma en un DTO detallado.
    @Transactional(readOnly = true) // Transacción de solo lectura, más eficiente.
    fun getHotelDetails(hotelId: Long): HotelDetailDto {
        // Buscamos el hotel o lanzamos una excepción si no existe.
        val hotel = hotelRepository.findById(hotelId)
            .orElseThrow { IllegalArgumentException("Hotel con ID $hotelId no encontrado.") }

        // Buscamos todos los tipos de habitación para ese hotel.
        val roomTypes = roomTypeRepository.findByHotelId(hotelId)

        // Convertimos la lista de entidades RoomType a una lista de DTOs.
        val roomDtos = roomTypes.map { room ->
            RoomTypeDetailDto(
                id = room.id,
                name = room.name,
                description = room.description,
                capacity = room.capacity,
                pricePerNight = room.pricePerNight
            )
        }

        // Creamos y devolvemos el DTO principal del hotel.
        return HotelDetailDto(
            id = hotel.id,
            name = hotel.name,
            address = hotel.address,
            city = hotel.city,
            stars = hotel.stars,
            rooms = roomDtos
        )
    }
}