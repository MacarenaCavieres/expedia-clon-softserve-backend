package com.expediaclon.backend.config

import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal

// @Component hace que Spring gestione esta clase como un bean.
// CommandLineRunner es una interfaz que hace que el método run() se ejecute al arrancar la aplicación.
@Component
class DataLoader(
    private val hotelRepository: HotelRepository,
    private val roomTypeRepository: RoomTypeRepository
) : CommandLineRunner {

    // Este método se ejecutará una vez que la aplicación haya iniciado.
    override fun run(vararg args: String?) {
        // Solo cargamos datos si la base de datos está vacía para no duplicar información.
        if (hotelRepository.count() == 0L) {
            println("Loading sample data for 3x3x3 rule...")
            loadData()
            println("Sample data loaded successfully.")
        }
    }

    private fun loadData() {
        // --- 1. París ---
        // Hotel 1.1
        val hotelParis1 = hotelRepository.save(Hotel(name = "Louvre Palace", city = "Paris", stars = 5, address = "123 Rue de Rivoli"))
        roomTypeRepository.save(RoomType(hotel = hotelParis1, name = "Standard Room", description = "A cozy room for two.", capacity = 2, pricePerNight = BigDecimal("250.00"), totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelParis1, name = "Superior Room", description = "A room with a beautiful city view.", capacity = 2, pricePerNight = BigDecimal("320.00"), totalInventory = 8))
        roomTypeRepository.save(RoomType(hotel = hotelParis1, name = "Family Suite", description = "A spacious suite for the whole family.", capacity = 4, pricePerNight = BigDecimal("450.00"), totalInventory = 5))

        // Hotel 1.2
        val hotelParis2 = hotelRepository.save(Hotel(name = "Le Marais Boutique", city = "Paris", stars = 4, address = "45 Rue des Francs-Bourgeois"))
        roomTypeRepository.save(RoomType(hotel = hotelParis2, name = "Petite Single", description = "Perfect for the solo traveler.", capacity = 1, pricePerNight = BigDecimal("180.00"), totalInventory = 15))
        roomTypeRepository.save(RoomType(hotel = hotelParis2, name = "Classic Double", description = "A classic and comfortable double room.", capacity = 2, pricePerNight = BigDecimal("240.00"), totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelParis2, name = "Deluxe Junior Suite", description = "Extra space and luxury amenities.", capacity = 3, pricePerNight = BigDecimal("350.00"), totalInventory = 5))

        // Hotel 1.3
        val hotelParis3 = hotelRepository.save(Hotel(name = "Montmartre View Inn", city = "Paris", stars = 3, address = "67 Rue de l'Abreuvoir"))
        roomTypeRepository.save(RoomType(hotel = hotelParis3, name = "Budget Double", description = "A simple and affordable room for two.", capacity = 2, pricePerNight = BigDecimal("130.00"), totalInventory = 20))
        roomTypeRepository.save(RoomType(hotel = hotelParis3, name = "Triple Room", description = "A room with three single beds.", capacity = 3, pricePerNight = BigDecimal("190.00"), totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelParis3, name = "Quad Room", description = "Ideal for a group of four.", capacity = 4, pricePerNight = BigDecimal("240.00"), totalInventory = 5))


        // --- 2. Roma ---
        // Hotel 2.1
        val hotelRoma1 = hotelRepository.save(Hotel(name = "The Roman Forum Hotel", city = "Rome", stars = 4, address = "456 Via dei Fori Imperiali"))
        roomTypeRepository.save(RoomType(hotel = hotelRoma1, name = "Single Room", description = "Perfect for solo travelers.", capacity = 1, pricePerNight = BigDecimal("120.00"), totalInventory = 8))
        roomTypeRepository.save(RoomType(hotel = hotelRoma1, name = "Superior Double Room", description = "A room with a balcony and a view.", capacity = 2, pricePerNight = BigDecimal("220.00"), totalInventory = 12))
        roomTypeRepository.save(RoomType(hotel = hotelRoma1, name = "Colosseum View Suite", description = "A premium suite with direct views of the Colosseum.", capacity = 4, pricePerNight = BigDecimal("500.00"), totalInventory = 3))

        // Hotel 2.2
        val hotelRoma2 = hotelRepository.save(Hotel(name = "Trastevere Charm B&B", city = "Rome", stars = 3, address = "78 Piazza di Santa Maria"))
        roomTypeRepository.save(RoomType(hotel = hotelRoma2, name = "Cozy Double", description = "A charming and quiet room.", capacity = 2, pricePerNight = BigDecimal("110.00"), totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelRoma2, name = "Terrace Room", description = "A beautiful room with a private terrace.", capacity = 2, pricePerNight = BigDecimal("160.00"), totalInventory = 5))
        roomTypeRepository.save(RoomType(hotel = hotelRoma2, name = "Apartment for Four", description = "A small apartment with a kitchenette.", capacity = 4, pricePerNight = BigDecimal("210.00"), totalInventory = 3))

        // Hotel 2.3
        val hotelRoma3 = hotelRepository.save(Hotel(name = "Vatican Garden Hotel", city = "Rome", stars = 5, address = "90 Via della Conciliazione"))
        roomTypeRepository.save(RoomType(hotel = hotelRoma3, name = "Deluxe King", description = "A luxurious room with a king-sized bed.", capacity = 2, pricePerNight = BigDecimal("350.00"), totalInventory = 15))
        roomTypeRepository.save(RoomType(hotel = hotelRoma3, name = "Twin Room", description = "A comfortable room with two single beds.", capacity = 2, pricePerNight = BigDecimal("320.00"), totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelRoma3, name = "St. Peter's Suite", description = "Unparalleled luxury with views of St. Peter's Basilica.", capacity = 4, pricePerNight = BigDecimal("900.00"), totalInventory = 2))


        // --- 3. Nueva York ---
        // Hotel 3.1
        val hotelNY1 = hotelRepository.save(Hotel(name = "Central Park Hotel", city = "New York", stars = 5, address = "789 Central Park South"))
        roomTypeRepository.save(RoomType(hotel = hotelNY1, name = "Queen Room", description = "A modern room with a queen-sized bed.", capacity = 2, pricePerNight = BigDecimal("350.00"), totalInventory = 20))
        roomTypeRepository.save(RoomType(hotel = hotelNY1, name = "King Room Park View", description = "A room with a king bed and park view.", capacity = 2, pricePerNight = BigDecimal("450.00"), totalInventory = 15))
        roomTypeRepository.save(RoomType(hotel = hotelNY1, name = "View Suite", description = "A luxury suite with a park view.", capacity = 4, pricePerNight = BigDecimal("750.00"), totalInventory = 3))

        // Hotel 3.2
        val hotelNY2 = hotelRepository.save(Hotel(name = "Brooklyn Bridge Loft", city = "New York", stars = 4, address = "12 Main Street, Brooklyn"))
        roomTypeRepository.save(RoomType(hotel = hotelNY2, name = "Standard Loft", description = "A stylish loft for two.", capacity = 2, pricePerNight = BigDecimal("280.00"), totalInventory = 12))
        roomTypeRepository.save(RoomType(hotel = hotelNY2, name = "Family Loft", description = "A two-level loft for families.", capacity = 5, pricePerNight = BigDecimal("420.00"), totalInventory = 6))
        roomTypeRepository.save(RoomType(hotel = hotelNY2, name = "Penthouse Suite", description = "Top floor suite with panoramic views.", capacity = 4, pricePerNight = BigDecimal("600.00"), totalInventory = 2))

        // Hotel 3.3
        val hotelNY3 = hotelRepository.save(Hotel(name = "Times Square Inn", city = "New York", stars = 3, address = "345 West 46th Street"))
        roomTypeRepository.save(RoomType(hotel = hotelNY3, name = "Compact Double", description = "A small, efficient room in the heart of the city.", capacity = 2, pricePerNight = BigDecimal("210.00"), totalInventory = 30))
        roomTypeRepository.save(RoomType(hotel = hotelNY3, name = "Theater View King", description = "A room overlooking the theater district.", capacity = 2, pricePerNight = BigDecimal("290.00"), totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelNY3, name = "Connecting Rooms", description = "Two connecting rooms for larger groups.", capacity = 6, pricePerNight = BigDecimal("480.00"), totalInventory = 5))
    }
}