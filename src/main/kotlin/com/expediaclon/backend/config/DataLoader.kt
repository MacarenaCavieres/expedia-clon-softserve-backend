package com.expediaclon.backend.config

import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.Destination
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.expediaclon.backend.repository.DestinationRepository
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

// @Component hace que Spring gestione esta clase como un bean.
// CommandLineRunner es una interfaz que hace que el método run() se ejecute al arrancar la aplicación.
@Component
class DataLoader(
    private val destinationRepository: DestinationRepository,
    private val hotelRepository: HotelRepository,
    private val roomRepository: RoomTypeRepository,
    private val bookingRepository: BookingRepository
) : CommandLineRunner {

    // Este método se ejecutará una vez que la aplicación haya iniciado.
    override fun run(vararg args: String?) {
        // Solo cargamos datos si la base de datos está vacía para no duplicar información.
        // Verificamos si hay destinos, ya que es la primera tabla que se inserta.
        if (destinationRepository.count() == 0L) {
            println("Loading sample data based on SQL schema...")
            loadData()
            println("Sample data loaded successfully.")
        }
    }

    private fun loadData() {
        // --- 1. Cargar Destinos (IDs 1, 2, 3) ---
        val paris = destinationRepository.save(Destination(id = 1, name = "Paris"))
        val rome = destinationRepository.save(Destination(id = 2, name = "Rome"))
        val newYork = destinationRepository.save(Destination(id = 3, name = "New York"))

        // -----------------------------------------------------------------------------------------
        // --- 2. Hoteles y Habitaciones para París (Destination ID 1) ---
        // -----------------------------------------------------------------------------------------

        // Hotel 1.1: Hotel Le Littré (ID 101)
        val hotelParis1 = hotelRepository.save(
            Hotel(
                name = "Hotel Le Littré",
                city = "Paris",
                rating = 9.5,
                comment = "Exceptional",
                latitude = 48.8437,
                longitude = 2.3276,
                description = "Luxembourg Gardens are just a stroll away from this charming Parisian stay. Relax in the sauna, enjoy buffet breakfast, or rent bikes to explore. With an airport shuttle and babysitting services on site, you can unwind with ease.",
                destination = paris,
                address = "9 Rue Littré, 75006 Paris, Francia",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/a19f6282.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/d1669741.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/b6b36f78.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/36ab6c4d.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/c5eba5b3.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones Hotel Le Littré (IDs 1001-1003)
        roomRepository.save(
            RoomType(
                id = 1001, hotel = hotelParis1, name = "Artist Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1800.00,
                description = "A cozy room for two.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/cb3868c6.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 1002, hotel = hotelParis1, name = "Deluxe Collection Room", capacity = 4, bedType = "DOUBLE",
                pricePerNight = 2000.00,
                description = "A room with a beautiful city view.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/2c4ffbe0.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 1003, hotel = hotelParis1, name = "Executive Room", capacity = 2, bedType = "SINGLE",
                pricePerNight = 1500.00,
                description = "A spacious suite for the whole family.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/2d4c38d4.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // Hotel 1.2: Hotel Malte (ID 102)
        val hotelParis2 = hotelRepository.save(
            Hotel(
                name = "Hotel Malte",
                city = "Paris",
                rating = 8.6,
                comment = "Very Good",
                latitude = 48.8656,
                longitude = 2.3364,
                description = "Elegant and cozy, this property offers a serene retreat with quiet inner court views and a welcoming lounge area. Enjoy a minibar, daily snacks, and access to sister hotels' drink bars, all within walking distance to the Louvre.",
                destination = paris,
                address = "63 Rue De Richelieu, Paris",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/bbf26628.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/d1460ade.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/fd9625df.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/e1317bd3.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/3586b8bf.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones Hotel Malte (IDs 1004-1006)
        roomRepository.save(
            RoomType(
                id = 1004, hotel = hotelParis2, name = "Executive Room", capacity = 3, bedType = "KING",
                pricePerNight = 1200.00,
                description = "Perfect for the solo traveler.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/013e9a6f.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 1005, hotel = hotelParis2, name = "Artist Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1280.00,
                description = "A classic and comfortable double room.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/cefd6f5b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 1006, hotel = hotelParis2, name = "Deluxe Collection Room", capacity = 4, bedType = "DOUBLE",
                pricePerNight = 1350.00,
                description = "Extra space and luxury amenities.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/fd9625df.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // Hotel 1.3: Hôtel Le Royal Monceau (ID 103)
        val hotelParis3 = hotelRepository.save(
            Hotel(

                name = "Hôtel Le Royal Monceau",
                city = "Paris",
                rating = 8.9,
                comment = "Excellent",
                latitude = 48.8785,
                longitude = 2.2965,
                description = "Superb location and stylish decor define this beautiful property, surrounded by stunning scenery and easily accessible to top attractions. Enjoy large, gorgeous rooms and exceptional service, with staff dedicated to making every detail perfect for your stay.",
                destination = paris,
                address = "37 Av. Hoche, 75008 Paris, Francia",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/63c4817a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/3c1a5867.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/b6924c2c.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/52a6344e.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/bcc79a8a.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones Hôtel Le Royal Monceau (IDs 1007-1009)
        roomRepository.save(
            RoomType(
                id = 1007, hotel = hotelParis3, name = "Executive Room", capacity = 2, bedType = "KING",
                pricePerNight = 1750.00,
                description = "A simple and affordable room for two.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/687e392b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 1008, hotel = hotelParis3, name = "Deluxe Collection Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1680.00,
                description = "A room with three single beds.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/6f821a3e.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 1009, hotel = hotelParis3, name = "Artist Room", capacity = 6, bedType = "KING",
                pricePerNight = 1100.0,
                description = "Ideal for a group of four.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/243d7269.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // -----------------------------------------------------------------------------------------
        // --- 3. Hoteles y Habitaciones para Roma (Destination ID 2) ---
        // -----------------------------------------------------------------------------------------

        // Hotel 2.1: The Hive Hotel (ID 201)
        val hotelRoma1 = hotelRepository.save(
            Hotel(

                name = "The Hive Hotel",
                city = "Rome",
                rating = 8.3,
                comment = "Very Good",
                latitude = 41.8986,
                longitude = 12.4939,
                description = "Modern design meets convenience, just steps from Rome's iconic attractions and train stations. Unwind at the rooftop bar with stunning views, or stay active in the well-equipped gym.",
                destination = rome,
                address = "Via Torino, 6, 00184 Rome RM, Italy",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/ec6c185a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/017e7631.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/cf6493ed.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/c6cfe293.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/6314e60e.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones The Hive Hotel (IDs 2001-2003)
        roomRepository.save(
            RoomType(
                id = 2001, hotel = hotelRoma1, name = "Artist Room", capacity = 2, bedType = "KING",
                pricePerNight = 1155.50,
                description = "Perfect for solo travelers.",
                imageUrl = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/9acbfc3e.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 2002, hotel = hotelRoma1, name = "Deluxe Collection Room", capacity = 1, bedType = "SINGLE",
                pricePerNight = 1110.00,
                description = "A room with a balcony and a view.",
                imageUrl = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/5388b3e5.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 2003, hotel = hotelRoma1, name = "Executive Room", capacity = 4, bedType = "DOUBLE",
                pricePerNight = 1210.00,
                description = "A premium suite with direct views of the Colosseum.",
                imageUrl = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/1b89248b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // Hotel 2.2: Hotel Artemide (ID 202)
        val hotelRoma2 = hotelRepository.save(
            Hotel(

                name = "Hotel Artemide",
                city = "Rome",
                rating = 9.7,
                comment = "Exceptional",
                latitude = 41.9037,
                longitude = 12.4897,
                description = "Rome’s iconic landmarks are just a stroll away from this stylish hotel. Treat yourself to a rooftop terrace, Italian cuisine, and rejuvenating spa treatments. A buffet breakfast, limo service, and multilingual staff ensure a seamless stay amidst the Eternal City’s timeless charm.",
                destination = rome,
                address = "Via Nazionale, 22, 00184 Rome RM, Italy",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/dccbfa6d.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/68d6e905.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/59690d15.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/4228e015.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/91bccfdf.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones Hotel Artemide (IDs 2004-2006)
        roomRepository.save(
            RoomType(
                id = 2004, hotel = hotelRoma2, name = "Executive Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1190.00,
                description = "Elegant 4-star hotel in the heart of Rome on the Via Nazionale.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/f42fba1f.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 2005, hotel = hotelRoma2, name = "Deluxe Collection Room", capacity = 3, bedType = "KING",
                pricePerNight = 1240.00,
                description = "A charming and quiet room.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/7a05c017.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 2006, hotel = hotelRoma2, name = "Artist Room", capacity = 2, bedType = "SINGLE",
                pricePerNight = 1160.00,
                description = "A beautiful room with a private terrace.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/de6a2231.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // Hotel 2.3: The Pantheon Iconic Rome Hotel (ID 203)
        val hotelRoma3 = hotelRepository.save(
            Hotel(

                name = "The Pantheon Iconic Rome Hotel",
                city = "Rome",
                rating = 8.8,
                comment = "Excellent",
                latitude = 41.8986,
                longitude = 12.4769,
                description = "Steps away from Rome's iconic Pantheon, this hotel offers a serene retreat amidst the city's vibrant attractions. Savor Michelin-star dining and breathtaking rooftop views, creating unforgettable moments in the heart of Rome.",
                destination = rome,
                address = "Via di S. Chiara, 4/A, 00186 Rome RM, Italy",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/1b1cd8f0.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/f7ee2a19.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/8815008d.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/13aeb6b6.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/091d2de9.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones The Pantheon Iconic Rome Hotel (IDs 2007-2009)
        roomRepository.save(
            RoomType(
                id = 2007, hotel = hotelRoma3, name = "Deluxe Collection Room", capacity = 2, bedType = "KING",
                pricePerNight = 1450.00,
                description = "A luxurious room with a king-sized bed.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/8a901565.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 2008, hotel = hotelRoma3, name = "Artist Room", capacity = 4, bedType = "DOUBLE",
                pricePerNight = 1550.00,
                description = "A comfortable room with two single beds.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/36b3de8b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 2009, hotel = hotelRoma3, name = "Executive Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1400.00,
                description = "Unparalleled luxury with views of St. Peter's Basilica.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/36b3de8b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // -----------------------------------------------------------------------------------------
        // --- 4. Hoteles y Habitaciones para Nueva York (Destination ID 3) ---
        // -----------------------------------------------------------------------------------------

        // Hotel 3.1: The New Yorker, A Wyndham Hotel (ID 301)
        val hotelNY1 = hotelRepository.save(
            Hotel(

                name = "The New Yorker, A Wyndham Hotel",
                city = "New York",
                rating = 9.2,
                comment = "Wonderful",
                latitude = 40.7520,
                longitude = -73.9935,
                description = "In the heart of Manhattan, discover a haven near iconic Macy's. Enjoy full breakfast, explore with tour assistance, and unwind at the 24-hour gym or on-site bar. With three restaurants to choose from, this urban gem is perfect for those who crave excitement and convenience.",
                destination = newYork,
                address = "481 8th Ave, New York, NY 10001, EE. UU.",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/cb62f2a4.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/d39e5614.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/35bdea24.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/695376e9.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/1e662ebe.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones The New Yorker (IDs 3001-3003)
        roomRepository.save(
            RoomType(
                id = 3001, hotel = hotelNY1, name = "Executive Room", capacity = 1, bedType = "SINGLE",
                pricePerNight = 1120.00,
                description = "A modern room with a queen-sized bed.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/d39e5614.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 3002, hotel = hotelNY1, name = "Artist Room", capacity = 2, bedType = "DOUBLE",
                pricePerNight = 1180.00,
                description = "A room with a king bed and park view.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/a00861e6.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 3003, hotel = hotelNY1, name = "Deluxe Collection Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1210.00,
                description = "A luxury suite with a park view.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/55cb9634.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // Hotel 3.2: The Plaza Hotel (ID 302)
        val hotelNY2 = hotelRepository.save(
            Hotel(

                name = "The Plaza Hotel",
                city = "New York",
                rating = 8.9,
                comment = "Excellent",
                latitude = 40.7646,
                longitude = -73.9749,
                description = "Stunning architecture and an iconic, serene ambiance define this luxurious property near Central Park. Enjoy elegantly decorated rooms, impeccable service, and a perfect location for exploring the city's finest shopping and dining.",
                destination = newYork,
                address = "768 5th Ave, New York, NY 10019, EE. UU",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/2549538f.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/ac66e6a0.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/c88b3360.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/5d7741e0.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/bccfdc8b.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones The Plaza Hotel (IDs 3004-3006)
        roomRepository.save(
            RoomType(
                id = 3004, hotel = hotelNY2, name = "Artist Room", capacity = 2, bedType = "KING",
                pricePerNight = 1990.00,
                description = "A stylish loft for two.",
                imageUrl = "https://images.trvl-media.com/lodging/10000000/9390000/9382200/9382123/67fc4d79.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 3005, hotel = hotelNY2, name = "Deluxe Collection Room", capacity = 4, bedType = "DOUBLE",
                pricePerNight = 1250.00,
                description = "A two-level loft for families.",
                imageUrl = "https://images.trvl-media.com/lodging/10000000/9390000/9382200/9382123/c9227d13.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 3006, hotel = hotelNY2, name = "Executive Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1850.00,
                description = "Top floor suite with panoramic views.",
                imageUrl = "https://images.trvl-media.com/lodging/10000000/9390000/9382200/9382123/5fd78ea2.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // Hotel 3.3: The Standard, High Line (ID 303)
        val hotelNY3 = hotelRepository.save(
            Hotel(

                name = "The Standard, High Line",
                city = "New York",
                rating = 8.5,
                comment = "Very Good",
                latitude = 40.7407,
                longitude = -74.0084,
                description = "Tennis anyone? This stylish gem near the Whitney Museum of American Art serves up a full breakfast, 4 bars, and a 24-hour gym. Unwind with concierge services or browse the gift shop. When you need some fresh air, step out onto the terrace or stroll over to Little Island and The High Line Park.",
                destination = newYork,
                address = "848 Washington St, New York, NY 10014, EE. UU",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/94886612.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/6954b2cb.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/ba107088.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/2657fd06.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/09570003.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        )
        // Habitaciones The Standard, High Line (IDs 3007-3009)
        roomRepository.save(
            RoomType(
                id = 3007, hotel = hotelNY3, name = "Executive Room", capacity = 2, bedType = "QUEEN",
                pricePerNight = 1380.00,
                description = "A small, efficient room in the heart of the city.",
                imageUrl = "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/bdf7b4bc.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 3008, hotel = hotelNY3, name = "Deluxe Collection Room", capacity = 2, bedType = "KING",
                pricePerNight = 1420.00,
                description = "A room overlooking the theater district.",
                imageUrl = "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/9873328d.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )
        roomRepository.save(
            RoomType(
                id = 3009, hotel = hotelNY3, name = "Artist Room", capacity = 1, bedType = "SINGLE",
                pricePerNight = 1300.00,
                description = "Two connecting rooms for larger groups.",
                imageUrl = "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/5de54bd7.jpg?impolicy=fcrop&w=1200&h=800&quality=medium"
            )
        )

        // -----------------------------------------------------------------------------------------
        // --- 5. Cargar Reservas ---
        // -----------------------------------------------------------------------------------------

        // Reserva Cancelada (ID 1)
        bookingRepository.save(
            Booking(
                checkInDate = LocalDate.of(2025, 11, 10),
                checkOutDate = LocalDate.of(2025, 11, 15),
                totalGuests = 2,
                guestNames = "Alice Johnson, Bob Johnson",
                totalPrice = 3900.00,
                status = BookingStatus.CANCELED,
                hotelName = "Hotel Le Littré",
                hotelCity = "Paris",
                hotelImage = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/a19f6282.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                roomId = 1001
            )
        )

        // Reserva Pendiente (ID 2)
        bookingRepository.save(
            Booking(
                checkInDate = LocalDate.of(2026, 1, 20),
                checkOutDate = LocalDate.of(2026, 1, 25),
                totalGuests = 1,
                guestNames = "Charlie Brown",
                totalPrice = 3625.00,
                status = BookingStatus.PENDING,
                hotelName = "The Hive Hotel",
                hotelCity = "Rome",
                hotelImage = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/ec6c185a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                roomId = 2002
            )
        )
    }
}