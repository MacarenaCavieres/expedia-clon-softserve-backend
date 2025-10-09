package com.expediaclon.backend.service

import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.repository.HotelRepository
import org.springframework.stereotype.Service

@Service
class HotelService(
    private val hotelRepository: HotelRepository
) {
    // Lógica para buscar hoteles, delegando la consulta al repositorio.
    fun searchHotels(city: String, passengerCount: Int): List<Hotel> {
        return hotelRepository.findHotelsInCityWithSufficientCapacity(city, passengerCount)
    }
}