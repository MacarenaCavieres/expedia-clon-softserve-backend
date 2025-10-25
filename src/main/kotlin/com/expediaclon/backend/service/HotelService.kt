package com.expediaclon.backend.service

// Asegúrate que las importaciones de DTOs apunten al archivo correcto (ej: dto.ApiDtos)
import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.dto.RoomTypeDetailDto
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
// findByIdOrNull necesita esta importación
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal // Importa BigDecimal

@Service
class HotelService(
    private val hotelRepository: HotelRepository,
    private val roomTypeRepository: RoomTypeRepository
) {

    // --- LÓGICA DE BÚSQUEDA ACTUALIZADA ---
    // Renombrado para claridad, usa la consulta con filtro de capacidad.
    @Transactional(readOnly = true) // Añadir transaccionalidad para relaciones LAZY si fueran necesarias
    fun searchHotels(city: String, passengerCount: Int): List<HotelCardDto> {
        // Usamos la consulta que filtra por ciudad Y capacidad mínima.
        val hotels = hotelRepository.findHotelsInCityWithSufficientCapacity(city, passengerCount)

        // Mapeamos las entidades Hotel a los DTOs que espera el frontend.
        return hotels.map { hotel ->
            // Buscamos las habitaciones para calcular el precio mínimo de forma segura
            // (evita problemas con carga LAZY fuera de transacción si la hubiera).
            val rooms = roomTypeRepository.findByHotelId(hotel.id)
            val minPrice = rooms.minOfOrNull { it.pricePerNight } ?: BigDecimal.ZERO // Usar BigDecimal

            // Obtenemos la primera imagen como imagen principal.
            val mainImage = hotel.images.firstOrNull() ?: ""

            // Creamos el DTO HotelCardDto alineado con frontend.
            HotelCardDto(
                id = hotel.id, // ID no es nullable
                name = hotel.name,
                rating = hotel.rating,
                comment = hotel.comment,
                pricePerNight = minPrice, // BigDecimal
                mainImage = mainImage,
                city = hotel.city
            )
        }
    }

    // --- LÓGICA DE DETALLES ACTUALIZADA ---
    @Transactional(readOnly = true)
    fun getHotelDetails(hotelId: Long): HotelDetailDto { // Devuelve no nullable o lanza excepción
        // findByIdOrNull es más seguro que findById().orElseThrow() dentro del servicio
        val hotel = hotelRepository.findByIdOrNull(hotelId)
            ?: throw IllegalArgumentException("Hotel con ID $hotelId no encontrado.") // Lanza excepción si no existe

        // Buscamos explícitamente las habitaciones asociadas.
        val roomTypes = roomTypeRepository.findByHotelId(hotelId)

        // Mapeamos las entidades RoomType a RoomTypeDetailDto.
        val roomDtos = roomTypes.map { mapToRoomDetailDto(it) }

        // Creamos el DTO HotelDetailDto alineado con frontend.
        return HotelDetailDto(
            id = hotel.id, // ID no es nullable
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

    // --- MAPEO DE HABITACIÓN ACTUALIZADO ---
    private fun mapToRoomDetailDto(roomEntity: RoomType): RoomTypeDetailDto {
        return RoomTypeDetailDto(
            id = roomEntity.id, // ID no es nullable
            capacity = roomEntity.capacity,
            name = roomEntity.name,
            bedType = roomEntity.bedType, // Ya es String, no necesita toString()
            pricePerNight = roomEntity.pricePerNight, // Ya es BigDecimal
            imageUrl = roomEntity.imageUrl,
            description = roomEntity.description // Añadido campo faltante
        )
    }
}