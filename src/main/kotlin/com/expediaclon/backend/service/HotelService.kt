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
import java.math.BigDecimal // Importa BigDecimal

/**
 * Capa de servicio que encapsula la lógica de negocio para las operaciones de Hoteles.
 *
 * Esta clase actúa como intermediario entre el HotelController y los repositorios,
 * manejando la lógica de búsqueda, filtrado, obtención de detalles y mapeo de DTOs.
 *
 * @property hotelRepository Repositorio para acceder a los datos de [Hotel].
 * @property roomTypeRepository Repositorio para acceder a los datos de [RoomType].
 */
@Service
class HotelService(
    private val hotelRepository: HotelRepository,
    private val roomTypeRepository: RoomTypeRepository
) {

    // --- LÓGICA DE BÚSQUEDA ---

    /**
     * Busca hoteles que coincidan con la ciudad y tengan al menos una habitación
     * con capacidad suficiente para el número de huéspedes.
     *
     * @param city Ciudad donde buscar el hotel.
     * @param passengerCount Número mínimo de huéspedes que la habitación debe soportar.
     * @return Una lista de [HotelCardDto] (vista resumida) para mostrar en los resultados de búsqueda.
     */
    // Añadir transaccionalidad para relaciones LAZY si fueran necesarias
    @Transactional(readOnly = true)
    fun searchHotels(city: String, passengerCount: Int): List<HotelCardDto> {

        // 1. Usamos la consulta personalizada que filtra por ciudad Y capacidad mínima.
        val hotels = hotelRepository.findHotelsInCityWithSufficientCapacity(city, passengerCount)

        // 2. Mapeamos las entidades Hotel a los DTOs que espera el frontend.
        return hotels.map { hotel ->

            // 3. Buscamos las habitaciones para calcular el precio mínimo de forma segura

            // --- ⚠️ NOTA DE RENDIMIENTO (Mentor) ---
            // Esta lógica (buscar rooms dentro del map) puede causar un problema de N+1 queries.
            // Si hay 10 hoteles, se ejecutarán 1 (hoteles) + 10 (rooms) = 11 queries.
            //
            // Una optimización futura podría ser:
            // 1. Obtener todos los IDs de hotel: val hotelIds = hotels.map { it.id }
            // 2. Buscar *todos* los rooms de esos hoteles en UNA sola query:
            //    val allRooms = roomTypeRepository.findByHotelIdIn(hotelIds)
            // 3. Agrupar los rooms por hotelId en un Map:
            //    val roomsByHotelId = allRooms.groupBy { it.hotel.id }
            // 4. Usar este Map en el bucle 'map' para encontrar el minPrice (mucho más rápido).
            // --- Fin de la Nota ---

            val rooms = roomTypeRepository.findByHotelId(hotel.id)
            val minPrice = rooms.minOfOrNull { it.pricePerNight } ?: BigDecimal.ZERO // Usar BigDecimal

            // 4. Obtenemos la primera imagen como imagen principal.
            val mainImage = hotel.images.firstOrNull() ?: ""

            // 5. Creamos el DTO HotelCardDto alineado con frontend.
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

        // 1. Busca el hotel por ID.
        // findByIdOrNull es más seguro que findById().orElseThrow() dentro del servicio
        // y nos permite lanzar una excepción más específica (IllegalArgumentException).
        val hotel = hotelRepository.findByIdOrNull(hotelId)
        // Lanza excepción si no existe (será capturada por el GlobalExceptionHandler -> 404)
            ?: throw IllegalArgumentException("Hotel con ID $hotelId no encontrado.")

        // 2. Buscamos explícitamente las habitaciones asociadas.
        val roomTypes = roomTypeRepository.findByHotelId(hotelId)

        // 3. Mapeamos las entidades RoomType a RoomTypeDetailDto usando el helper.
        val roomDtos = roomTypes.map { mapToRoomDetailDto(it) }

        // 4. Creamos el DTO HotelDetailDto alineado con frontend.
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

    // --- MAPEO DE HABITACIÓN (Helper) ---

    /**
     * Función helper privada para convertir una entidad [RoomType] a su DTO [RoomTypeDetailDto].
     *
     * @param roomEntity La entidad de la base de datos.
     * @return El DTO con los datos formateados para el frontend.
     */
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