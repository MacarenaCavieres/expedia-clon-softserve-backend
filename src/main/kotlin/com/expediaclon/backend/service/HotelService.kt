package com.expediaclon.backend.service

import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.dto.RoomTypeDetailDto
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelService(
    private val hotelRepository: HotelRepository,
    // Necesitamos el repositorio de habitaciones para obtener los detalles.
    private val roomTypeRepository: RoomTypeRepository
) {

    // Lógica para buscar hoteles (sin cambios).
    fun findAllHotelsByCity(city: String, passengerCount: Int): List<HotelCardDto> {
        val hotels = hotelRepository.findByCity(city)
        return hotels.map { hotel ->
            val minPrice = hotel.rooms.minOfOrNull { it.pricePerNight } ?: 0.0
            val mainImage = hotel.images.firstOrNull()

            HotelCardDto(
                id = hotel.id ?: throw IllegalStateException("Hotel ID cannot be null"),
                name = hotel.name,
                rating = hotel.rating,
                comment = hotel.comment,
                pricePerNight = minPrice,
                mainImage = mainImage ?: "",
                city = hotel.city
            )
        }
    }

    // Busca un hotel por su ID y lo transforma en un DTO detallado.
    @Transactional(readOnly = true) // Transacción de solo lectura, más eficiente.
    fun getHotelDetails(hotelId: Long): HotelDetailDto? {
        val hotel = hotelRepository.findByIdOrNull(hotelId) ?: return null

        return hotel.let {
            val roomDtos = it.rooms.map { roomEntity ->
                mapToRoomDetailDto(roomEntity)
            }

            HotelDetailDto(
                id = it.id ?: throw IllegalStateException("Hotel ID cannot be null"),
                name = it.name,
                rating = it.rating,
                comment = it.comment,
                description = it.description,
                city = it.city,
                latitude = it.latitude,
                longitude = it.longitude,
                address = it.address,
                images = it.images,
                rooms = roomDtos
            )
        }
    }

    private fun mapToRoomDetailDto(roomEntity: RoomType): RoomTypeDetailDto {
        return RoomTypeDetailDto(
            id = roomEntity.id ?: throw IllegalStateException("Room ID cannot be null"),
            capacity = roomEntity.capacity,
            name = roomEntity.name,
            bedType = roomEntity.bedType.toString(),
            pricePerNight = roomEntity.pricePerNight,
            imageUrl = roomEntity.imageUrl
        )
    }
}