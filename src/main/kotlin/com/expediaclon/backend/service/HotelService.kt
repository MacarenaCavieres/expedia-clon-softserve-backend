package com.expediaclon.backend.service

import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.dto.RoomTypeDetailDto
import com.expediaclon.backend.exception.BadRequestMessageException
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * @property hotelRepository Repositorio para acceder a los datos de [Hotel].
 * @property roomTypeRepository Repositorio para acceder a los datos de [RoomType].
 */
@Service
class HotelService(
    private val hotelRepository: HotelRepository,
    private val roomTypeRepository: RoomTypeRepository
) {
    /**
     * @param city Ciudad donde buscar el hotel.
     * @param passengerCount Número mínimo de huéspedes que la habitación debe soportar.
     * @return Una lista de [HotelCardDto] (vista resumida) para mostrar en los resultados de búsqueda.
     */
    @Transactional(readOnly = true)
    fun searchHotels(city: String, passengerCount: Int): List<HotelCardDto> {
        val hotels = hotelRepository.findHotelsInCityWithSufficientCapacity(city, passengerCount)

        return hotels.map { hotel ->
            val minPrice = hotel.rooms.minOfOrNull { it.pricePerNight } ?: BigDecimal.ZERO
            val mainImage = hotel.images.firstOrNull() ?: ""

            HotelCardDto(
                id = hotel.id,
                name = hotel.name,
                rating = hotel.rating,
                comment = hotel.comment,
                pricePerNight = minPrice.toPlainString(),
                mainImage = mainImage,
                city = hotel.city
            )
        }
    }

    // --- LÓGICA DE DETALLES ---

    /**
     * Obtiene la vista detallada de un hotel específico, incluyendo todos sus tipos de habitación.
     *
     * @param hotelId El ID del hotel a consultar.
     * @return El [HotelDetailDto] con la información completa.
     * @throws IllegalArgumentException Si no se encuentra un hotel con el ID proporcionado.
     */
    @Transactional(readOnly = true)
    fun getHotelDetails(hotelId: Long): HotelDetailDto { // Devuelve no nullable o lanza excepción

        val hotel = hotelRepository.findByIdOrNull(hotelId)
            ?: throw BadRequestMessageException("Hotel with ID $hotelId not found.")
        val roomTypes = roomTypeRepository.findByHotelId(hotelId)
        val roomDtos = roomTypes.map { mapToRoomDetailDto(it) }

        return HotelDetailDto(
            id = hotel.id,
            name = hotel.name,
            rating = hotel.rating,
            comment = hotel.comment,
            description = hotel.description,
            city = hotel.city,
            latitude = hotel.latitude,
            longitude = hotel.longitude,
            address = hotel.address,
            images = hotel.images,
            rooms = roomDtos
        )
    }

    // --- MAPEO DE HABITACIÓN (Helper) ---

    /**
     * Función helper privada para convertir una entidad [RoomType] a su DTO [RoomTypeDetailDto].
     *
     * @param roomEntity La entidad de la base de datos.
     * @return El DTO con los datos formateados para el frontend.
     */
    private fun mapToRoomDetailDto(roomEntity: RoomType): RoomTypeDetailDto {
        return RoomTypeDetailDto(
            id = roomEntity.id,
            capacity = roomEntity.capacity,
            name = roomEntity.name,
            bedType = roomEntity.bedType,
            pricePerNight = roomEntity.pricePerNight,
            imageUrl = roomEntity.imageUrl,
            description = roomEntity.description
        )
    }
}