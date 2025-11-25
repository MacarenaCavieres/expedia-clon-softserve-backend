package com.expediaclon.backend.config

import java.util.UUID
import com.expediaclon.backend.model.Booking
import com.expediaclon.backend.model.Hotel
import com.expediaclon.backend.model.RoomType
import com.expediaclon.backend.model.User
import com.expediaclon.backend.model.enums.BookingStatus
import com.expediaclon.backend.repository.BookingRepository
import com.expediaclon.backend.repository.HotelRepository
import com.expediaclon.backend.repository.RoomTypeRepository
import com.expediaclon.backend.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

@Component
class DataLoader(
    private val hotelRepository: HotelRepository,
    private val roomTypeRepository: RoomTypeRepository,
    private val bookingRepository: BookingRepository,
    private val userRepository: UserRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (hotelRepository.count() == 0L) {
            println("Loading sample data...")
            loadData()
            println("Sample data loaded successfully.")
        }
    }

    private fun loadData() {
        // --- Hoteles y Habitaciones para París ---
        val hotelParis1 = hotelRepository.save(
            Hotel(
                name = "Hotel Le Littré",
                city = "Paris",
                rating = 9.5,
                comment = "Exceptional",
                latitude = 48.8437,
                longitude = 2.3276,
                description = "Luxembourg Gardens are just a stroll away from this charming Parisian stay. Relax in the sauna, enjoy buffet breakfast, or rent bikes to explore. With an airport shuttle and babysitting services on site, you can unwind with ease.",
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
        val roomParis1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis1,
                name = "Artist Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("199"),
                description = "A cozy room for two.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/cb3868c6.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //
        val roomParis1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis1,
                name = "Deluxe Collection Room",
                capacity = 4,
                bedType = "DOUBLE",
                pricePerNight = BigDecimal("253"),
                description = "A room with a beautiful city view.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/2c4ffbe0.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 5
            )
        ) //
        val roomParis1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis1,
                name = "Executive Room",
                capacity = 2,
                bedType = "SINGLE",
                pricePerNight = BigDecimal("292"),
                description = "A spacious suite for the whole family.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/1700/1688/2d4c38d4.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 8
            )
        ) //

        val hotelParis2 = hotelRepository.save(
            Hotel(
                name = "Hotel Malte",
                city = "Paris",
                rating = 8.6,
                comment = "Very Good",
                latitude = 48.8656,
                longitude = 2.3364,
                description = "Elegant and cozy, this property offers a serene retreat with quiet inner court views and a welcoming lounge area. Enjoy a minibar, daily snacks, and access to sister hotels' drink bars, all within walking distance to the Louvre.",
                address = "63 Rue De Richelieu, Paris",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/bbf26628.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/d1460ade.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/fd9625df.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/e1317bd3.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/3586b8bf.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomParis2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis2,
                name = "Executive Room",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("282"),
                description = "Perfect for the solo traveler.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/013e9a6f.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 7
            )
        ) //
        val roomParis2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis2,
                name = "Artist Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("336"),
                description = "A classic and comfortable double room.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/cefd6f5b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //
        val roomParis2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis2,
                name = "Deluxe Collection Room",
                capacity = 4,
                bedType = "DOUBLE",
                pricePerNight = BigDecimal("282"),
                description = "Extra space and luxury amenities.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/60000/53600/53587/fd9625df.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 5
            )
        ) //

        val hotelParis3 = hotelRepository.save(
            Hotel(
                name = "Hôtel Le Royal Monceau",
                city = "Paris",
                rating = 8.9,
                comment = "Excellent",
                latitude = 48.8785,
                longitude = 2.2965,
                description = "Superb location and stylish decor define this beautiful property, surrounded by stunning scenery and easily accessible to top attractions. Enjoy large, gorgeous rooms and exceptional service, with staff dedicated to making every detail perfect for your stay.",
                address = "37 Av. Hoche, 75008 Paris, Francia",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/63c4817a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/3c1a5867.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/b6924c2c.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/52a6344e.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/bcc79a8a.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomParis3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis3,
                name = "Executive Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("1100"),
                description = "A simple and affordable room for two.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/687e392b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //
        val roomParis3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis3,
                name = "Deluxe Collection Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("1210"),
                description = "A room with three single beds.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/6f821a3e.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 12
            )
        ) //
        val roomParis3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelParis3,
                name = "Artist Room",
                capacity = 6,
                bedType = "KING",
                pricePerNight = BigDecimal("1576"),
                description = "Ideal for a group of four.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/14600/14582/243d7269.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 4
            )
        ) //


        // --- Hoteles y Habitaciones para Roma ---
        val hotelRoma1 = hotelRepository.save(
            Hotel(
                name = "The Hive Hotel",
                city = "Rome",
                rating = 8.3,
                comment = "Very Good",
                latitude = 41.8986,
                longitude = 12.4939,
                description = "Modern design meets convenience, just steps from Rome's iconic attractions and train stations. Unwind at the rooftop bar with stunning views, or stay active in the well-equipped gym.",
                address = "Via Torino, 6, 00184 Rome RM, Italy",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/ec6c185a.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/017e7631.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/cf6493ed.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/c6cfe293.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/6314e60e.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomRoma1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma1,
                name = "Artist Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("182"),
                description = "Perfect for solo travelers.",
                imageUrl = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/9acbfc3e.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //
        val roomRoma1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma1,
                name = "Deluxe Collection Room",
                capacity = 1,
                bedType = "SINGLE",
                pricePerNight = BigDecimal("221"),
                description = "A room with a balcony and a view.",
                imageUrl = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/5388b3e5.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 8
            )
        ) //
        val roomRoma1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma1,
                name = "Executive Room",
                capacity = 4,
                bedType = "DOUBLE",
                pricePerNight = BigDecimal("201"),
                description = "A premium suite with direct views.",
                imageUrl = "https://images.trvl-media.com/lodging/25000000/24470000/24463300/24463268/1b89248b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 6
            )
        ) //

        val hotelRoma2 = hotelRepository.save(
            Hotel(
                name = "Hotel Artemide",
                city = "Rome",
                rating = 9.7,
                comment = "Exceptional",
                latitude = 41.9037,
                longitude = 12.4897,
                description = "Rome’s iconic landmarks are just a stroll away from this stylish hotel. Treat yourself to a rooftop terrace, Italian cuisine, and rejuvenating spa treatments. A buffet breakfast, limo service, and multilingual staff ensure a seamless stay amidst the Eternal City’s timeless charm.",
                address = "Via Nazionale, 22, 00184 Rome RM, Italy",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/dccbfa6d.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/68d6e905.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/59690d15.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/4228e015.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/91bccfdf.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomRoma2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma2,
                name = "Executive Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("262"),
                description = "Elegant 4-star hotel in the heart of Rome on the Via Nazionale.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/f42fba1f.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomRoma2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma2,
                name = "Deluxe Collection Room",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("308"),
                description = "A charming and quiet room.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/7a05c017.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //
        val roomRoma2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma2,
                name = "Artist Room",
                capacity = 2,
                bedType = "SINGLE",
                pricePerNight = BigDecimal("308"),
                description = "A beautiful room with a private terrace.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/45900/45856/de6a2231.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 12
            )
        ) //

        val hotelRoma3 = hotelRepository.save(
            Hotel(
                name = "The Pantheon Iconic Rome Hotel",
                city = "Rome",
                rating = 8.8,
                comment = "Excellent",
                latitude = 41.8986,
                longitude = 12.4769,
                description = "Steps away from Rome's iconic Pantheon, this hotel offers a serene retreat amidst the city's vibrant attractions. Savor Michelin-star dining and breathtaking rooftop views, creating unforgettable moments in the heart of Rome.",
                address = "Via di S. Chiara, 4/A, 00186 Rome RM, Italy",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/1b1cd8f0.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/f7ee2a19.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/8815008d.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/13aeb6b6.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/091d2de9.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomRoma3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma3,
                name = "Deluxe Collection Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("470"),
                description = "A luxurious room with a king-sized bed.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/8a901565.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //
        val roomRoma3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma3,
                name = "Artist Room",
                capacity = 4,
                bedType = "DOUBLE",
                pricePerNight = BigDecimal("497"),
                description = "A comfortable room with two single beds.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/36b3de8b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 7
            )
        ) //
        val roomRoma3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRoma3,
                name = "Executive Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("581"),
                description = "Unparalleled luxury with views of St. Peter's Basilica.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23980000/23972600/23972532/36b3de8b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 8
            )
        ) //


        // --- Hoteles y Habitaciones para Nueva York ---
        val hotelNY1 = hotelRepository.save(
            Hotel(
                name = "The New Yorker, A Wyndham Hotel",
                city = "New York",
                rating = 9.2,
                comment = "Wonderful",
                latitude = 40.7520,
                longitude = -73.9935,
                description = "In the heart of Manhattan, discover a haven near iconic Macy's. Enjoy full breakfast, explore with tour assistance, and unwind at the 24-hour gym or on-site bar. With three restaurants to choose from, this urban gem is perfect for those who crave excitement and convenience.",
                address = "481 8th Ave, New York, NY 10001, EE. UU.",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/cb62f2a4.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/d39e5614.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/35bdea24.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/695376e9.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/1e662ebe.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomNY1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY1,
                name = "Executive Room",
                capacity = 1,
                bedType = "SINGLE",
                pricePerNight = BigDecimal("125"),
                description = "A modern room with a queen-sized bed.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/d39e5614.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomNY1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY1,
                name = "Artist Room",
                capacity = 2,
                bedType = "DOUBLE",
                pricePerNight = BigDecimal("132"),
                description = "A room with a king bed and park view.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/a00861e6.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomNY1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY1,
                name = "Deluxe Collection Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("167"),
                description = "A luxury suite with a park view.",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/41100/41009/55cb9634.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        ) //

        val hotelNY2 = hotelRepository.save(
            Hotel(
                name = "The Plaza Hotel",
                city = "New York",
                rating = 8.9,
                comment = "Excellent",
                latitude = 40.7646,
                longitude = -73.9749,
                description = "Stunning architecture and an iconic, serene ambiance define this luxurious property near Central Park. Enjoy elegantly decorated rooms, impeccable service, and a perfect location for exploring the city's finest shopping and dining.",
                address = "768 5th Ave, New York, NY 10019, EE. UU",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/2549538f.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/ac66e6a0.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/c88b3360.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/5d7741e0.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/1000000/30000/28100/28044/bccfdc8b.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomNY2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY2,
                name = "Artist Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("697"),
                description = "A stylish loft for two.",
                imageUrl = "https://images.trvl-media.com/lodging/10000000/9390000/9382200/9382123/67fc4d79.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 8
            )
        ) //
        val roomNY2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY2,
                name = "Deluxe Collection Room",
                capacity = 4,
                bedType = "DOUBLE",
                pricePerNight = BigDecimal("763"),
                description = "A two-level loft for families.",
                imageUrl = "https://images.trvl-media.com/lodging/10000000/9390000/9382200/9382123/c9227d13.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 5
            )
        ) //
        val roomNY2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY2,
                name = "Executive Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("833"),
                description = "Top floor suite with panoramic views.",
                imageUrl = "https://images.trvl-media.com/lodging/10000000/9390000/9382200/9382123/5fd78ea2.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 7
            )
        ) //

        val hotelNY3 = hotelRepository.save(
            Hotel(
                name = "The Standard, High Line",
                city = "New York",
                rating = 8.5,
                comment = "Very Good",
                latitude = 40.7407,
                longitude = -74.0084,
                description = "Tennis anyone? This stylish gem near the Whitney Museum of American Art serves up a full breakfast, 4 bars, and a 24-hour gym. Unwind with concierge services or browse the gift shop. When you need some fresh air, step out onto the terrace or stroll over to Little Island and The High Line Park.",
                address = "848 Washington St, New York, NY 10014, EE. UU",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/94886612.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/6954b2cb.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/ba107088.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/2657fd06.jpg?impolicy=resizecrop&ra=fit&rw=455&rh=455",
                    "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/09570003.jpg?impolicy=resizecrop&rw=1200&ra=fit"
                )
            )
        ) //
        val roomNY3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY3,
                name = "Executive Room",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("290"),
                description = "A small, efficient room in the heart of the city.",
                imageUrl = "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/bdf7b4bc.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomNY3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY3,
                name = "Deluxe Collection Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("307"),
                description = "A room overlooking the theater district.",
                imageUrl = "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/9873328d.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomNY3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelNY3,
                name = "Artist Room",
                capacity = 1,
                bedType = "SINGLE",
                pricePerNight = BigDecimal("328"),
                description = "Two connecting rooms for larger groups.",
                imageUrl = "https://images.trvl-media.com/lodging/11000000/10640000/10630200/10630123/5de54bd7.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelPuntaCana1 = hotelRepository.save(
            Hotel(
                name = "Secrets Tides Punta Cana All Inclusive - Adults Only",
                city = "Punta Cana",
                rating = 9.4,
                comment = "Exceptional",
                latitude = 18.8085003,
                longitude = -68.5815235,
                description = "Secrets Tides Punta Cana is an all-inclusive, adults-only resort situated on the beautiful Uvero Alto beach. It offers a luxurious experience complete with tranquil settings, gourmet dining, and high-quality service. It is designed for romantic getaways and pure relaxation.",
                address = "Playa Uvero Alto Km 276, Punta Cana, La Altagracia",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/59fb67d4.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/bf394ea0.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/5d061bd0.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/aa45a3e0.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/0eb06a55.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomPC1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana1,
                name = "Junior Suite Tropical View Double",
                capacity = 3,
                bedType = "2 Double Beds",
                pricePerNight = BigDecimal("910"),
                description = "A small, efficient room in the heart of the city.",
                imageUrl = "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/cf0e4895.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomPC1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana1,
                name = "Junior Suite Garden View Double",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("953"),
                description = "A room overlooking the theater district.",
                imageUrl = "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/98bb7983.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomPC1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana1,
                name = "Junor Suite Partial Ocean View King",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("996"),
                description = "Two connecting rooms for larger groups.",
                imageUrl = "https://images.trvl-media.com/lodging/100000000/99470000/99467000/99466918/39553af8.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelPuntaCana2 = hotelRepository.save(
            Hotel(
                name = "Unique Club at Lopesan Costa Bávaro - All Inclusive",
                city = "Punta Cana",
                rating = 8.4,
                comment = "Very Good",
                latitude = 18.5601,
                longitude = 68.3725,
                description = "Experience premium all-inclusive luxury at the Unique Club at Lopesan Costa Bávaro. Located in the heart of Punta Cana, this resort offers exclusive amenities and access to stunning Bávaro beach. Ideal for families and couples alike, it provides an unparalleled tropical getaway with diverse dining options and exciting entertainment.",
                address = "Punta Cana, Punta Cana, La Altagracia, 23301",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/d06939d0.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/4d97d1db.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/25d0b2c1.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/ff355e3e.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/3f460f21.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomPC2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana2,
                name = "Unique King - Tropical Junior Suite",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("499"),
                description = "Free welcome drink, Free water park passes",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/d006d6d4.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomPC2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana2,
                name = "Unique King - Ocean View Junior Suite",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("538"),
                description = "Free minibar",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/5040f230.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomPC2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana2,
                name = "Unique Queen - Tropical Junior Suite",
                capacity = 4,
                bedType = "2 Double Beds",
                pricePerNight = BigDecimal("499"),
                description = "Two connecting rooms for larger groups.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/45b249ce.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelPuntaCana3 = hotelRepository.save(
            Hotel(
                name = "Reserva Real by Harper",
                city = "Punta Cana",
                rating = 9.2,
                comment = "Wonderful",
                latitude = 18.6826,
                longitude = 68.4239,
                description = "Reserva Real by Harper offers a wonderful stay with a stellar 9.2 rating in the vibrant area of Avenida Alemania. Enjoy a seamless experience with fantastic amenities near the beach. This property promises an elegant and unforgettable vacation in the heart of Punta Cana's resort district.",
                address = "Avenida Alemania, Punta Cana, La Altagracia, 23000",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/f63097e1.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/cfba3265.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/70b8d5e6.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/fa68d419.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/299d3eb2.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomPC3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana3,
                name = "One Bedroom Deluxe",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("72"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/2a8c2f82.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomPC3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana3,
                name = "Two Bedroom Deluxe",
                capacity = 4,
                bedType = "2 King Beds",
                pricePerNight = BigDecimal("81"),
                description = "Free minibar",
                imageUrl = "https://images.trvl-media.com/lodging/113000000/112840000/112836300/112836293/1492caba.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomPC3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana3,
                name = "Unique Queen - Tropical Junior Suite",
                capacity = 4,
                bedType = "2 Double Beds",
                pricePerNight = BigDecimal("92"),
                description = "Two connecting rooms for larger groups.",
                imageUrl = "https://images.trvl-media.com/lodging/24000000/23540000/23535300/23535297/45b249ce.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelPuntaCana4 = hotelRepository.save(
            Hotel(
                name = "Four Points by Sheraton Puntacana Village",
                city = "Punta Cana",
                rating = 9.0,
                comment = "Wonderful",
                latitude = 18.5746,
                longitude = 68.3619,
                description = "Located on the convenient Boulevard 1ero de Noviembre, the Four Points by Sheraton Puntacana Village offers a wonderful 9.0 rated stay. This hotel provides a modern and comfortable experience close to the airport and local amenities. Guests can enjoy reliable service and a seamless base for exploring the Punta Cana area.",
                address = "Boulevard 1ero De Noviembre, Punta Cana, La Altagracia, 23000",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/88f3ad6a.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/9b879368.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/b700097d.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/124c5ad3.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/5d537ca3.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomPC4_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana4,
                name = "Traditional Room, 1 King Bed",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("184"),
                description = "Free WiFi",
                imageUrl = "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/c9ebe375.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomPC4_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana4,
                name = "Traditional Room, 2 Double Beds",
                capacity = 4,
                bedType = "2 Double Beds",
                pricePerNight = BigDecimal("184"),
                description = "Free minibar",
                imageUrl = "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/cb8c2fc5.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomPC4_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelPuntaCana4,
                name = "Suite, 1 Bedroom",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("324"),
                description = "City View",
                imageUrl = "https://images.trvl-media.com/lodging/6000000/5400000/5395800/5395756/2cdb13ce.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelMaldives1 = hotelRepository.save(
            Hotel(
                name = "Gili Lankanfushi Maldives",
                city = "Maldives",
                rating = 9.6,
                comment = "Exceptional",
                latitude = 4.294849,
                longitude = 73.558063,
                description = "A Maldivian paradise with rustic charm, this serene island retreat offers privacy and breathtaking views. Delight in exceptional culinary experiences, spacious Duplex villas, and unforgettable underwater explorations, all enhanced by impeccable service and personalized touches.",
                address = "Lankanfushi Island, Lankanfushi Island",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/8f8480ff.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/69f99a3d.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/3ae5d23a.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/e461df4f.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/7a381b76.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomMaldives1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives1,
                name = "Villa Suite",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("2003"),
                description = "Reserve now, pay deposit",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/6309345e.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomMaldives1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives1,
                name = "Gili Lagoon Villa",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("2383"),
                description = "Reserve now, pay deposit",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/a8c7c624.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomMaldives1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives1,
                name = "Crusoe Residence Island view",
                capacity = 4,
                bedType = "KING",
                pricePerNight = BigDecimal("2493"),
                description = "Reserve now, pay deposit",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1720000/1717200/1717151/e461df4f.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelMaldives2 = hotelRepository.save(
            Hotel(
                name = "Barcelo Nasandhura Male",
                city = "Maldives",
                rating = 8.4,
                comment = "Very Good",
                latitude = 4.1788,
                longitude = 73.5132,
                description = "Located in the heart of Malé on Boduthakurufaanu Magu, the Barcelo Nasandhura Male offers a convenient and comfortable 8.4-rated stay. This urban hotel serves as an ideal base for exploring the capital city and conducting business. Guests can enjoy modern amenities and easy access to local shops and attractions. It provides a unique blend of city life and Maldivian charm.",
                address = "NPH Investment, PVT LTD,Hotel Apartment, 4th Floor Office,Boduthakurufaanu Magu, Malé, 20026",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/6e3abdee.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/w5757h3835x3y5-e01fcaf3.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/95a243c9.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/726f6e75.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/w5755h3840x5y0-b1f30680.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomMaldives2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives2,
                name = "Superior Room, Ocean View",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("252"),
                description = "Free roundtrip airport transfer for 2 (on request)",
                imageUrl = "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/w5760h3835x0y5-daeb8815.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomMaldives2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives2,
                name = "Room, Ocean View (Panoramic)",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("280"),
                description = "Free roundtrip airport transfer for 2 (on request)",
                imageUrl = "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/0e0c836c.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomMaldives2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives2,
                name = "Superior Room, Oceanfront",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("305"),
                description = "Free roundtrip airport transfer for 2 (on request)",
                imageUrl = "https://images.trvl-media.com/lodging/110000000/109450000/109448700/109448686/w5755h3835x5y5-9ea47a60.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelMaldives3 = hotelRepository.save(
            Hotel(
                name = "Villa Nautica Paradise Island Resort",
                city = "Maldives",
                rating = 9.2,
                comment = "Wonderful",
                latitude = 4.294849,
                longitude = 73.558063,
                description = "Sun-kissed beaches and lush greenery create a breathtaking paradise at this luxurious island resort. Delight in exceptional service, diverse activities, and exquisite dining, all while enjoying unobstructed sunrise views and pristine snorkeling spots.",
                address = "North Male Atoll, Lankanfinolhu, 2002",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/a6f0490c.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/d36f4e67.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/c88da88f.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/d76bb275.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/0fcf8be8.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomMaldives3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives3,
                name = "Beach Villa",
                capacity = 3,
                bedType = "1 Double Bed and 1 Double Sofa Bed",
                pricePerNight = BigDecimal("487"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/27f707bf.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomMaldives3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives3,
                name = "Deluxe Beach Pool Villa",
                capacity = 3,
                bedType = "1 Double Bed and 1 Double Sofa Bed",
                pricePerNight = BigDecimal("663"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/3a3988b9.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomMaldives3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives3,
                name = "Sunset Beach Pool Villa",
                capacity = 3,
                bedType = "1 Double Bed and 1 Double Sofa Bed",
                pricePerNight = BigDecimal("705"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1940000/1932500/1932406/79dbe9c9.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelMaldives4 = hotelRepository.save(
            Hotel(
                name = "Sheraton Maldives Full Moon Resort & Spa",
                city = "Maldives",
                rating = 8.8,
                comment = "Excellent",
                latitude = 4.259,
                longitude = 73.546,
                description = "Overwater bungalows offer spacious rooms with private pools and bicycles, creating a serene escape surrounded by warm, azure waters. Delight in exquisite dining options and thrilling on-site water activities, ensuring every moment is magical.",
                address = "Furanafushi Island, Furanafushi Island",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/f51a19f4.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/844e5e33.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/7b1281c1.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/ab904207.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/50c1bcfa.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomMaldives4_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives4,
                name = "Deluxe Room, 1 King Bed, Beach View",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("385"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/9e9105dd.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomMaldives4_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives4,
                name = "Cottage, 1 King Bed, Beach View",
                capacity = 4,
                bedType = "KING",
                pricePerNight = BigDecimal("460"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/06379006.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomMaldives4_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives4,
                name = "Deluxe Room, 2 Double Beds, Beach View",
                capacity = 3,
                bedType = "2 Double Beds",
                pricePerNight = BigDecimal("385"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/2000000/1330000/1326500/1326412/d6fd5efc.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelMaldives5 = hotelRepository.save(
            Hotel(
                name = "Cocomo Maldives",
                city = "Maldives",
                rating = 10.0,
                comment = "Exceptional",
                latitude = 4.309,
                longitude = 73.57,
                description = "Experience perfection at the exceptional Cocomo Maldives, an exquisite retreat on Furanafushi Island boasting a flawless 10.0 rating. This luxury resort surrounds guests with warm, azure waters and offers spacious overwater bungalows with private pools for the ultimate relaxation. Indulge in exquisite dining options and participate in thrilling water activities designed to make every moment magical. It is the quintessential Maldivian getaway for discerning travelers seeking unparalleled service and natural beauty.",
                address = "Venice, Athiri Magu, Himmafushi, Himmafushi, 08060",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/88ade631.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/1a374d78.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/7a134788.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/3f021c5d.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/bfc0f651.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomMaldives5_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives5,
                name = "Deluxe Double Room With Private Balcony",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("118"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/2767a988.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        ) //
        val roomMaldives5_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives5,
                name = "Deluxe Double Room with Private Balcony",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("126"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/c06ec15d.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomMaldives5_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelMaldives5,
                name = "Executive Suite With Sea View & Balcony",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("138"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/112000000/111080000/111071900/111071890/d2edc608.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelTokyo1 = hotelRepository.save(
            Hotel(
                name = "InterContinental Tokyo Bay by IHG",
                city = "Tokyo",
                rating = 9.0,
                comment = "Wonderful",
                latitude = 35.658,
                longitude = 139.752,
                description = "Located in Minato with a wonderful 9.0 rating, the InterContinental Tokyo Bay offers stunning views over the water. This hotel blends convenience with luxury, featuring modern amenities and elegant design. It provides a sophisticated escape from the bustling city life. Ideal for both business and leisure travelers seeking a premium experience in Tokyo.",
                address = "1-16-2 Kaigan, Minato, Tokyo, Tokyo-to, 105-8576",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/77971be1.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/d9e4a6db.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/c984201f.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/4d4ebd0b.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/047a6517.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomTokyo1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo1,
                name = "Classic Room",
                capacity = 2,
                bedType = "2 Twin Beds OR 1 King Bed",
                pricePerNight = BigDecimal("226"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/9870432a.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomTokyo1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo1,
                name = "Classic Room, 2 Twin Beds, River View (High Floor)",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("226"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/5f761b52.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        ) //
        val roomTokyo1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo1,
                name = "Classic Room, 2 Twin Beds, River View",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("226"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/20000/15300/15228/06a190f8.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelTokyo2 = hotelRepository.save(
            Hotel(
                name = "Mercure Tokyo Hibiya",
                city = "Tokyo",
                rating = 8.4,
                comment = "Very Good",
                latitude = 35.6675,
                longitude = 139.757,
                description = "Located in the prestigious Chiyoda-ku district with a \"Very Good\" 8.4 rating, the Mercure Tokyo Hibiya provides stylish and modern accommodation. Situated near the Imperial Palace and Hibiya Park, it offers easy access to the city’s key cultural landmarks and business districts. This hotel blends French hospitality with Japanese efficiency, providing a comfortable and convenient stay for both leisure and corporate travelers. It's an ideal base for exploring the vibrant heart of Tokyo.",
                address = "1-5-2 Uchisaiwai-cho, Chiyoda-ku, Tokyo, Tokyo-to, 100-0011",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/e6f2d4b0.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/49843f22.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/09866728.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/w2410h3622x0y0-61b50d0f.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/ca3a556f.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomTokyo2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo2,
                name = "Superior Room with 1 King Size Bed, Non Smoking",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("219"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/281dba8c.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomTokyo2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo2,
                name = "Privilege Room, Lounge Access, 1 King Size Bed, Non Smoking",
                capacity = 3,
                bedType = "KING",
                pricePerNight = BigDecimal("278"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/24d7e389.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomTokyo2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo2,
                name = "Superior Room with 2 Single Beds, Non Smoking",
                capacity = 3,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("219"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/3700/3640/ec226f96.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelTokyo3 = hotelRepository.save(
            Hotel(
                name = "Shinjuku Washington Hotel Annex",
                city = "Tokyo",
                rating = 8.2,
                comment = "Very Good",
                latitude = 35.6883,
                longitude = 139.6990,
                description = "The Shinjuku Washington Hotel Annex offers a \"Very Good\" 8.2 rated stay in the heart of the vibrant Nishi-Shinjuku district. This hotel provides convenient access to the Tokyo Metropolitan Government Building and Shinjuku Station. It's an ideal choice for travelers seeking comfortable accommodation with excellent transport links and proximity to major city attractions.",
                address = "3-2-9 Nishi-Shinjuku, Shinjuku-ku, Tokyo, Tokyo-to, 160-8336",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/bbab33c7.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/35354b19.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/1e8dd953.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/f64c29e6.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/c0e7fb4a.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomTokyo3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo3,
                name = "Double Room, Non Smoking",
                capacity = 3,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("147"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/121d5bb9.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomTokyo3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo3,
                name = "Privilege Room, Lounge Access, 1 King Size Bed, Non Smoking",
                capacity = 3,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("156"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/38982921.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomTokyo3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo3,
                name = "Triple Room, Non Smoking",
                capacity = 3,
                bedType = "3 Twin Beds",
                pricePerNight = BigDecimal("242"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/8000000/7620000/7616300/7616260/9da8fae9.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelTokyo4 = hotelRepository.save(
            Hotel(
                name = "Rembrandt Cabin Shinjuku Shin-Okubo",
                city = "Tokyo",
                rating = 8.8,
                comment = "Excellent",
                latitude = 35.7001,
                longitude = 139.7011,
                description = "Rated an \"Excellent\" 8.8, Rembrandt Cabin Shinjuku Shin-Okubo offers a unique and highly-rated capsule hotel experience. Conveniently located near Shin-Okubo station, it provides easy access to the vibrant Korean district and major transit hubs. Ideal for budget-conscious travelers, this accommodation offers a clean, efficient, and comfortable stay in a lively area of Tokyo.",
                address = "1-4-15 Hyakunincho, Shinjuku, Tokyo, Tokyo, 169-0073",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/2dcd0164.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/cf569655.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/d7f67e73.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/79bf009c.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/6cc5abeb.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomTokyo4_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo4,
                name = "2 Capsule Rooms in Female Dormitory Room",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("92"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/ddf3088a.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomTokyo4_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo4,
                name = "Male Dormitory Room (2 Capsule Rooms)",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("92"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/c162f7eb.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomTokyo4_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo4,
                name = "Room for Male (Lower Floor), Non Smoking",
                capacity = 1,
                bedType = "1 Twin Bed",
                pricePerNight = BigDecimal("90"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/68000000/67290000/67281000/67280986/46d88b8e.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelTokyo5 = hotelRepository.save(
            Hotel(
                name = "Daiwa Roynet Hotel Nishi Shinjuku PREMIER",
                city = "Tokyo",
                rating = 9.4,
                comment = "Exceptional",
                latitude = 35.6896,
                longitude = 139.6953,
                description = "Rated an \"Exceptional\" 9.4, the Daiwa Roynet Hotel Nishi Shinjuku PREMIER offers a luxurious experience in the vibrant Shinjuku district. This hotel provides excellent service, modern amenities, and convenient access to key transport links and local attractions. It is an ideal choice for travelers seeking premium comfort and a superb location in the heart of Tokyo.",
                address = "6-12-39, Nishi-Shinjuku, Shinjuku-ku, Tokyo, Tokyo, 160-0023",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/455c3fac.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/w3990h2990x5y5-20a7e1b1.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/4399b910.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/w1992h1125x5y0-8af89a3e.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/w4000h2992x0y4-31baef56.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomTokyo5_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo5,
                name = "Standard Double Room with one Simmons bed 154cm wide, Non Smoking",
                capacity = 2,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("185"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/w3995h2995x0y5-af13fcab.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomTokyo5_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo5,
                name = "Standard Twin Room with two Simmons beds 110cm wide, Non Smoking",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("216"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/8f170974_edited_d7ad.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomTokyo5_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelTokyo5,
                name = "Family Room with two Simmons beds 110cm wide, one extra bed 92cm wide, Non Smoking",
                capacity = 3,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("230"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/28000000/27050000/27048400/27048315/8550452d_edited_418b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelLondon1 = hotelRepository.save(
            Hotel(
                name = "The Prince Akatoki London",
                city = "London",
                rating = 9.6,
                comment = "Exceptional",
                latitude = 51.5160,
                longitude = -0.1600,
                description = "Serene and stylish, this tranquil haven near Marble Arch exudes a unique Japanese charm. Delight in superb in-house dining, expertly mixed cocktails, and a fabulous afternoon tea.",
                address = "50 Great Cumberland Place, Marble Arch, London, England, W1H 7FD",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/ac0d3a5e.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/d8af54b8.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/22414e04.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/813b28ed.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/08190475.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomLondon1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon1,
                name = "Deluxe Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("346"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/2c722b0b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomLondon1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon1,
                name = "Executive Room",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("448"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/ab025592.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomLondon1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon1,
                name = "Executive Suite (Junior)",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("512"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/4000000/3010000/3005800/3005785/d6877d22.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelLondon2 = hotelRepository.save(
            Hotel(
                name = "The Hoxton Holborn",
                city = "London",
                rating = 9.2,
                comment = "Wonderful",
                latitude = 51.5172,
                longitude = -0.1220,
                description = "Rated a wonderful 9.2, The Hoxton Holborn offers a vibrant and stylish stay in the heart of central London. Conveniently located near the British Museum and Holborn station, it provides easy access to key attractions and transport links. This hotel features quirky design, comfortable rooms, and a buzzing atmosphere perfect for the modern traveler.",
                address = "199 - 206 High Holborn, London, England, WC1V 7BD",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/8ce26e73.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/2c417601.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/6503f989.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/f6055aa9.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/9db689d9.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomLondon2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon2,
                name = "Shoebox",
                capacity = 2,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("213"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/bdbf3e06.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomLondon2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon2,
                name = "Snug",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("222"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/275248fc.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomLondon2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon2,
                name = "Cosy Double",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("213"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/9000000/8300000/8296300/8296282/ab51a84a.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelLondon3 = hotelRepository.save(
            Hotel(
                name = "Sea Containers London",
                city = "London",
                rating = 9.4,
                comment = "Exceptional",
                latitude = 51.507957,
                longitude = -0.10703,
                description = "Stylish and modern, this hotel offers stunning views of the Thames and iconic landmarks like St. Paul's Cathedral. Enjoy the rooftop bar with panoramic city views, a beautiful spa, and richly appointed rooms for a truly memorable stay.",
                address = "20 Upper Ground, London, England, SE1 9PD",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/68467637.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/c0441196.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/8fd968db.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/15c582a2.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/a645709a.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomLondon3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon3,
                name = "Standard Room, 1 Queen Bed",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("213"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/782438ae.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomLondon3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon3,
                name = "Superior Room, 1 Queen Bed, City View",
                capacity = 2,
                bedType = "QUEEN",
                pricePerNight = BigDecimal("229"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/698d8096.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomLondon3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelLondon3,
                name = "Studio",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("274"),
                description = "Reserve now, pay later",
                imageUrl = "https://images.trvl-media.com/lodging/9000000/8470000/8466300/8466215/6f98a235.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelRio1 = hotelRepository.save(
            Hotel(
                name = "Fairmont Rio de Janeiro Copacabana",
                city = "Rio de Janeiro",
                rating = 9.2,
                comment = "Wonderful",
                latitude = -22.986389,
                longitude = -43.189444,
                description = "Perched on the serene Copacabana Beach, this beautiful hotel offers magnificent ocean views and a tranquil atmosphere. Delight in two stunning pools, a luxurious spa, and a top-tier restaurant, all complemented by exceptional service.",
                address = "Avenida Atlântica, 4240 - Copacabana, Rio de Janeiro, RJ, 22070-002",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/7b1127c2.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/b6567209.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/fab82fcd.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/a0bbee61.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/671caccc.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomRio1_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio1,
                name = "Fairmont, Room, 1 King Bed, City View",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("412"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/efd306af.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomRio1_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio1,
                name = "Deluxe Room, 1 King Bed, City View",
                capacity = 2,
                bedType = "KING",
                pricePerNight = BigDecimal("479"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/db4f2245.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomRio1_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio1,
                name = "Fairmont, Room, 2 Twin Beds, City View",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("412"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/4c62ba06.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelRio2 = hotelRepository.save(
            Hotel(
                name = "Sol Ipanema Hotel",
                city = "Rio de Janeiro",
                rating = 8.8,
                comment = "Excellent",
                latitude = -22.98688,
                longitude = -43.20455,
                description = "Located directly on the prestigious Avenida Vieira Souto, the Sol Ipanema Hotel boasts an \"Excellent\" 8.8 rating and superb oceanfront access. Guests are just steps from the famous Ipanema beach and a short stroll from vibrant local culture and shops. This hotel provides a comfortable and convenient base for exploring Rio, featuring a rooftop pool with panoramic views of the sea and the Dois Irmãos mountains. It offers an authentic and memorable stay in one of the city's most desirable locations.",
                address = "Avenida Vieira Souto 320, Ipanema, Rio de Janeiro, RJ, 22420-004",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/00ab5436.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/d5109d96.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/28d98f01.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/7b1621e1.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/3df944eb.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomRio2_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio2,
                name = "Standard Room",
                capacity = 3,
                bedType = "1 King Bed OR 2 Twin Beds",
                pricePerNight = BigDecimal("322"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/0d416183.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomRio2_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio2,
                name = "Superior Room",
                capacity = 3,
                bedType = "1 King Bed OR 2 Twin Beds",
                pricePerNight = BigDecimal("371"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/180000/174200/174198/db4f2245.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomRio2_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio2,
                name = "Family Room, Beach View",
                capacity = 4,
                bedType = "2 Queen Beds",
                pricePerNight = BigDecimal("548"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/50000/48100/48031/1ce1cb9d.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelRio3 = hotelRepository.save(
            Hotel(
                name = "Arena Ipanema Hotel",
                city = "Rio de Janeiro",
                rating = 8.8,
                comment = "Excellent",
                latitude = -22.9871,
                longitude = -43.1918,
                description = "The Arena Ipanema Hotel, boasting an \"Excellent\" 8.8 rating, enjoys a prime location on Rua Francisco Otaviano, close to both Ipanema and Copacabana beaches. This hotel offers an ideal base for exploring the vibrant lifestyle of Rio de Janeiro. With modern amenities and a fantastic location near Arpoador, guests can enjoy the best the city has to offer.",
                address = "Rua Francisco Otaviano 131, Rio de Janeiro, RJ, 22080-040",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/f4556e2e.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/eca472ac.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/6fab50a0.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/3e71e25c.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/b2375bbd.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomRio3_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio3,
                name = "Standard Double Room",
                capacity = 2,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("287"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/011634ca.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomRio3_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio3,
                name = "Superior Double Room",
                capacity = 2,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("330"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/674f8162.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomRio3_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio3,
                name = "Superior Room, 2 Twin Beds",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("330"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/14000000/13240000/13235700/13235684/7ff871aa.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        val hotelRio4 = hotelRepository.save(
            Hotel(
                name = "Miramar by Windsor Copacabana",
                city = "Rio de Janeiro",
                rating = 9.6,
                comment = "Exceptional",
                latitude = -22.98086,
                longitude = -43.18967,
                description = "Perfectly situated on Copacabana Beach, this beautifully decorated hotel offers modern, comfortable rooms and easy access to vibrant city attractions. Unwind at the stunning rooftop pool and bar, savoring breathtaking views and a delicious breakfast buffet.",
                address = "Avenida Atlantica, 3668 - Copacabana, Rio de Janeiro, RJ, 22070-001",
                images = mutableListOf(
                    "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/b01f6953.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/002aa641.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/15d4126e.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/e0ae5e65.jpg?impolicy=resizecrop&rw=598&ra=fit",
                    "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/50ac6153.jpg?impolicy=resizecrop&rw=598&ra=fit"
                )
            )
        )

        val roomRio4_1 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio4,
                name = "Standard com Cama Casal",
                capacity = 2,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("180"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/9769d9a2.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 20
            )
        )
        val roomRio4_2 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio4,
                name = "Superior com Cama Casal",
                capacity = 3,
                bedType = "1 Double Bed",
                pricePerNight = BigDecimal("207"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/2b4177fc.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 15
            )
        )
        val roomRio4_3 = roomTypeRepository.save(
            RoomType(
                hotel = hotelRio4,
                name = "Standard com 2 Camas de Solteiro Adaptado",
                capacity = 2,
                bedType = "2 Twin Beds",
                pricePerNight = BigDecimal("180"),
                description = "High-speed internet access",
                imageUrl = "https://images.trvl-media.com/lodging/1000000/10000/9100/9027/c1c4db0b.jpg?impolicy=fcrop&w=1200&h=800&quality=medium",
                totalInventory = 10
            )
        )

        // cargar el usuario

        val userCreator = userRepository.save(
            User(
                email = "creator@example.com",
                phone = "123456789",
                password = "hashed_password",
                name = "Test",
                lastname = "Creator"
            )
        )

        // --- Cargar Reservas de Muestra (Corregido para coincidir con Booking.kt) ---

        bookingRepository.save(
            Booking(
                passengerCount = 2,
                guestNames = "Alice Johnson, Bob Johnson",
                roomType = roomParis1_1,
                checkInDate = LocalDate.of(2025, 11, 10),
                checkOutDate = LocalDate.of(2025, 11, 15),
                totalPrice = roomParis1_1.pricePerNight.multiply(BigDecimal(5)),
                status = BookingStatus.CANCELLED,
                user = userCreator
            )
        )

        bookingRepository.save(
            Booking(
                passengerCount = 1,
                guestNames = "Charlie Brown",
                roomType = roomRoma1_1,
                checkInDate = LocalDate.of(2026, 1, 20),
                checkOutDate = LocalDate.of(2026, 1, 25),
                totalPrice = roomRoma1_1.pricePerNight.multiply(BigDecimal(5)),
                status = BookingStatus.PENDING,
                user = userCreator
            )
        )
    }
}