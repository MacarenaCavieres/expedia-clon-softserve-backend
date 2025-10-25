package com.expediaclon.backend.config

import java.util.UUID
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

@Component
class DataLoader(
    private val hotelRepository: HotelRepository,
    // Renombrado para consistencia con la interfaz
    private val roomTypeRepository: RoomTypeRepository,
    private val bookingRepository: BookingRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Verifica si la tabla de hoteles está vacía
        if (hotelRepository.count() == 0L) {
            println("Loading sample data...")
            loadData()
            println("Sample data loaded successfully.")
        }
    }

    private fun loadData() {

        // --- Hoteles y Habitaciones para París ---
        val hotelParis1 = hotelRepository.save( Hotel(name = "Hotel Le Littré", city = "Paris", rating = 9.5, comment = "Exceptional", latitude = 48.8437, longitude = 2.3276, description = "Luxembourg Gardens are just a stroll away...", address = "9 Rue Littré, 75006 Paris, Francia", images = mutableListOf("https://images.trvl-media.com/lodging/1000000/10000/1700/1688/a19f6282.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/d1669741.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/b6b36f78.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/36ab6c4d.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/c5eba5b3.jpg?impolicy=resizecrop&rw=1200&ra=fit")))
        val roomParis1_1 = roomTypeRepository.save(RoomType(hotel = hotelParis1, name = "Artist Room", capacity = 2, bedType = "QUEEN", pricePerNight = BigDecimal("1800.00"), description = "A cozy room for two.", imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/cb3868c6.jpg?impolicy=fcrop&w=1200&h=800&quality=medium", totalInventory = 10))
        val roomParis1_2 = roomTypeRepository.save(RoomType(hotel = hotelParis1, name = "Deluxe Collection Room", capacity = 4, bedType = "DOUBLE", pricePerNight = BigDecimal("2000.00"), description = "A room with a beautiful city view.", imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/2c4ffbe0.jpg?impolicy=fcrop&w=1200&h=800&quality=medium", totalInventory = 5))
        val roomParis1_3 = roomTypeRepository.save(RoomType(hotel = hotelParis1, name = "Executive Room", capacity = 2, bedType = "SINGLE", pricePerNight = BigDecimal("1500.00"), description = "A spacious suite for the whole family.", imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/2d4c38d4.jpg?impolicy=fcrop&w=1200&h=800&quality=medium", totalInventory = 8))

        val hotelParis2 = hotelRepository.save( Hotel(name = "Hotel Malte", city = "Paris", rating = 8.6, comment = "Very Good", latitude = 48.8656, longitude = 2.3364, description = "Elegant and cozy...", address = "63 Rue De Richelieu, Paris", images = mutableListOf("https://images.trvl-media.com/lodging/1000000/60000/53600/53587/bbf26628.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/d1460ade.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "...")))
        roomTypeRepository.save(RoomType(hotel=hotelParis2, name="Executive Room", capacity=3, bedType="KING", pricePerNight=BigDecimal("1200.00"), description="Perfect for the solo traveler.", imageUrl="...", totalInventory=7))
        roomTypeRepository.save(RoomType(hotel=hotelParis2, name="Artist Room", capacity=2, bedType="QUEEN", pricePerNight=BigDecimal("1280.00"), description="A classic and comfortable double room.", imageUrl="...", totalInventory=10))
        roomTypeRepository.save(RoomType(hotel=hotelParis2, name="Deluxe Collection Room", capacity=4, bedType="DOUBLE", pricePerNight=BigDecimal("1350.00"), description="Extra space and luxury amenities.", imageUrl="...", totalInventory=5))

        val hotelParis3 = hotelRepository.save( Hotel(name = "Hôtel Le Royal Monceau", city = "Paris", rating = 8.9, comment = "Excellent", latitude = 48.8785, longitude = 2.2965, description = "Superb location...", address = "37 Av. Hoche, 75008 Paris, Francia", images = mutableListOf("https://images.trvl-media.com/lodging/1000000/20000/14600/14582/63c4817a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "...")))
        roomTypeRepository.save(RoomType(hotel = hotelParis3, name = "Executive Room", capacity = 2, bedType = "KING", pricePerNight = BigDecimal("1750.00"), description = "A simple and affordable room for two.", imageUrl = "...", totalInventory = 10))
        roomTypeRepository.save(RoomType(hotel = hotelParis3, name = "Deluxe Collection Room", capacity = 2, bedType = "QUEEN", pricePerNight = BigDecimal("1680.00"), description = "A room with three single beds.", imageUrl = "...", totalInventory = 12))
        roomTypeRepository.save(RoomType(hotel = hotelParis3, name = "Artist Room", capacity = 6, bedType = "KING", pricePerNight = BigDecimal("1100.00"), description = "Ideal for a group of four.", imageUrl = "...", totalInventory = 4))


        // --- Hoteles y Habitaciones para Roma ---
        val hotelRoma1 = hotelRepository.save( Hotel(name = "The Hive Hotel", city = "Rome", rating = 8.3, comment = "Very Good", latitude = 41.8986, longitude = 12.4939, description = "Modern design...", address = "Via Torino, 6, 00184 Rome RM, Italy", images = mutableListOf("https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/ec6c185a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "...")))
        val roomRoma1_1 = roomTypeRepository.save(RoomType(hotel = hotelRoma1, name = "Artist Room", capacity = 2, bedType = "KING", pricePerNight = BigDecimal("1155.50"), description = "Perfect for solo travelers.", imageUrl = "...", totalInventory=10 ))
        val roomRoma1_2 = roomTypeRepository.save(RoomType(hotel = hotelRoma1, name = "Deluxe Collection Room", capacity = 1, bedType = "SINGLE", pricePerNight = BigDecimal("1110.00"), description = "A room with a balcony and a view.", imageUrl = "...", totalInventory = 8))
        val roomRoma1_3 = roomTypeRepository.save(RoomType(hotel = hotelRoma1, name = "Executive Room", capacity = 4, bedType = "DOUBLE", pricePerNight = BigDecimal("1210.00"), description = "A premium suite with direct views.", imageUrl = "...", totalInventory = 6))

        val hotelRoma2 = hotelRepository.save( Hotel(name="Hotel Artemide", city="Rome", rating=9.7, comment="Exceptional", latitude=41.9037, longitude=12.4897, description="Rome’s iconic landmarks...", address="Via Nazionale, 22, 00184 Rome RM, Italy", images=mutableListOf("...")))
        roomTypeRepository.save(RoomType(hotel=hotelRoma2, name="Executive Room", capacity=2, bedType="QUEEN", pricePerNight=BigDecimal("1190.00"), description="Elegant 4-star hotel...", imageUrl="...", totalInventory=15))
        roomTypeRepository.save(RoomType(hotel=hotelRoma2, name="Deluxe Collection Room", capacity=3, bedType="KING", pricePerNight=BigDecimal("1240.00"), description="A charming and quiet room.", imageUrl="...", totalInventory=10))
        roomTypeRepository.save(RoomType(hotel=hotelRoma2, name="Artist Room", capacity=2, bedType="SINGLE", pricePerNight=BigDecimal("1160.00"), description="A beautiful room with a private terrace.", imageUrl="...", totalInventory=12))

        val hotelRoma3 = hotelRepository.save( Hotel(name="The Pantheon Iconic Rome Hotel", city="Rome", rating=8.8, comment="Excellent", latitude=41.8986, longitude=12.4769, description="Steps away from Rome's iconic Pantheon...", address="Via di S. Chiara, 4/A, 00186 Rome RM, Italy", images=mutableListOf("...")))
        roomTypeRepository.save(RoomType(hotel=hotelRoma3, name="Deluxe Collection Room", capacity=2, bedType="KING", pricePerNight=BigDecimal("1450.00"), description="A luxurious room with a king-sized bed.", imageUrl="...", totalInventory=10))
        roomTypeRepository.save(RoomType(hotel=hotelRoma3, name="Artist Room", capacity=4, bedType="DOUBLE", pricePerNight=BigDecimal("1550.00"), description="A comfortable room with two single beds.", imageUrl="...", totalInventory=7))
        roomTypeRepository.save(RoomType(hotel=hotelRoma3, name="Executive Room", capacity=2, bedType="QUEEN", pricePerNight=BigDecimal("1400.00"), description="Unparalleled luxury with views...", imageUrl="...", totalInventory=8))


        // --- Hoteles y Habitaciones para Nueva York ---
        val hotelNY1 = hotelRepository.save( Hotel(name = "The New Yorker, A Wyndham Hotel", city = "New York", rating = 9.2, comment = "Wonderful", latitude = 40.7520, longitude = -73.9935, description = "In the heart of Manhattan...", address = "481 8th Ave, New York, NY 10001, EE. UU.", images = mutableListOf("https://images.trvl-media.com/lodging/1000000/50000/41100/41009/cb62f2a4.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455", "...")))
        roomTypeRepository.save(RoomType(hotel = hotelNY1, name = "Executive Room", capacity = 1, bedType = "SINGLE", pricePerNight = BigDecimal("1120.00"), description = "A modern room with a queen-sized bed.", imageUrl = "...", totalInventory=15))
        roomTypeRepository.save(RoomType(hotel = hotelNY1, name = "Artist Room", capacity = 2, bedType = "DOUBLE", pricePerNight = BigDecimal("1180.00"), description = "A room with a king bed and park view.", imageUrl = "...", totalInventory=20))
        roomTypeRepository.save(RoomType(hotel = hotelNY1, name = "Deluxe Collection Room", capacity = 2, bedType = "QUEEN", pricePerNight = BigDecimal("1210.00"), description = "A luxury suite with a park view.", imageUrl = "...", totalInventory=10))

        val hotelNY2 = hotelRepository.save( Hotel(name="The Plaza Hotel", city="New York", rating=8.9, comment="Excellent", latitude=40.7646, longitude=-73.9749, description="Stunning architecture...", address="768 5th Ave, New York, NY 10019, EE. UU", images=mutableListOf("...")))
        roomTypeRepository.save(RoomType(hotel=hotelNY2, name="Artist Room", capacity=2, bedType="KING", pricePerNight=BigDecimal("1990.00"), description="A stylish loft for two.", imageUrl="...", totalInventory=8))
        roomTypeRepository.save(RoomType(hotel=hotelNY2, name="Deluxe Collection Room", capacity=4, bedType="DOUBLE", pricePerNight=BigDecimal("1250.00"), description="A two-level loft for families.", imageUrl="...", totalInventory=5))
        roomTypeRepository.save(RoomType(hotel=hotelNY2, name="Executive Room", capacity=2, bedType="QUEEN", pricePerNight=BigDecimal("1850.00"), description="Top floor suite with panoramic views.", imageUrl="...", totalInventory=7))

        val hotelNY3 = hotelRepository.save( Hotel(name="The Standard, High Line", city="New York", rating=8.5, comment="Very Good", latitude=40.7407, longitude=-74.0084, description="Tennis anyone?...", address="848 Washington St, New York, NY 10014, EE. UU", images=mutableListOf("...")))
        roomTypeRepository.save(RoomType(hotel=hotelNY3, name="Executive Room", capacity=2, bedType="QUEEN", pricePerNight=BigDecimal("1380.00"), description="A small, efficient room...", imageUrl="...", totalInventory=20))
        roomTypeRepository.save(RoomType(hotel=hotelNY3, name="Deluxe Collection Room", capacity=2, bedType="KING", pricePerNight=BigDecimal("1420.00"), description="A room overlooking the theater district.", imageUrl="...", totalInventory=15))
        roomTypeRepository.save(RoomType(hotel=hotelNY3, name="Artist Room", capacity=1, bedType="SINGLE", pricePerNight=BigDecimal("1300.00"), description="Two connecting rooms...", imageUrl="...", totalInventory=10))


        // --- Cargar Reservas de Muestra (Corregido para coincidir con Booking.kt) ---

        // Reserva Cancelada
        bookingRepository.save(
            Booking(
                sessionId = UUID.randomUUID(), // ID de sesión para invitado
                passengerCount = 2,
                guestNames = "Alice Johnson, Bob Johnson",
                roomType = roomParis1_1, // Referencia a la entidad RoomType guardada
                checkInDate = LocalDate.of(2025, 11, 10),
                checkOutDate = LocalDate.of(2025, 11, 15),
                // Calcular precio basado en la habitación y noches
                totalPrice = roomParis1_1.pricePerNight.multiply(BigDecimal(5)),
                confirmationCode = "CANCEL01", // Código único
                status = BookingStatus.CANCELED // Usar el enum
            )
        )

        // Reserva Pendiente (o Confirmada)
        bookingRepository.save(
            Booking(
                sessionId = UUID.randomUUID(),
                passengerCount = 1,
                guestNames = "Charlie Brown",
                roomType = roomRoma1_1, // Referencia a la entidad RoomType guardada
                checkInDate = LocalDate.of(2026, 1, 20),
                checkOutDate = LocalDate.of(2026, 1, 25),
                // Calcular precio basado en la habitación y noches
                totalPrice = roomRoma1_1.pricePerNight.multiply(BigDecimal(5)),
                confirmationCode = "PENDING1", // Código único
                status = BookingStatus.PENDING // O CONFIRMED
            )
        )
    }
}