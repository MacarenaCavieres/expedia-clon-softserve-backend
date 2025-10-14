# Expedia Clone - Backend API

This repository contains the source code for the Expedia Clone project's backend. The API is built with Kotlin and Spring Boot and is responsible for managing hotels, rooms, bookings, and the application's core business logic.

---

## 🚀 Tech Stack

* **Language:** Kotlin
* **Framework:** Spring Boot 3.x
* **Database:** MySQL
* **Data Access:** Spring Data JPA / Hibernate
* **Dependency Management:** Maven

---

## 🏛️ Architecture

The backend follows a classic layered architecture to separate concerns and improve maintainability:

* **Controller Layer:** Exposes the REST API endpoints. It is the entry point for all frontend requests and its sole responsibility is to handle HTTP traffic.
* **Service Layer:** Contains all the business logic. It orchestrates operations, performs calculations, and communicates with the repository layer.
* **Repository Layer:** Manages all communication with the database. It uses Spring Data JPA to abstract SQL queries.

---

## 📋 Data Model

The data model for the MVP is centered around two main concepts: **Inventory** and **Bookings**.

* **`User`**: Represents the application's users (to be implemented in a future iteration).
* **`Hotel`**: Represents the inventory of available hotels.
* **`RoomType`**: Defines the different types of rooms for each hotel, including their price and capacity.
* **`Booking`**: The core transactional entity that links a guest's session to a specific `RoomType`.

---

## ✅ Prerequisites

To run this project on your local machine, you will need the following installed:

* **Java JDK 17** or higher.
* **Apache Maven**.
* A running **MySQL server** instance.

---

## ⚙️ Local Environment Setup

Follow these steps to configure your database and connect the application.

**1. Create the Database**
Connect to your local MySQL server (using MySQL Workbench or your preferred client) and run the following command:
```sql
CREATE DATABASE expedia_clone_db;
```

**2. Create a Dedicated User**
For security reasons, it's good practice to create a specific user for the application instead of using root.

```sql
-- Replace 'your_secure_password' with a strong password
CREATE USER 'expedia_app'@'localhost' IDENTIFIED BY 'your_secure_password';

-- Grant all privileges on the project's database to the new user
GRANT ALL PRIVILEGES ON expedia_clone_db.* TO 'expedia_app'@'localhost';
```


**3. Configure the Application**
In the src/main/resources/ directory, find and edit the application.properties file. Ensure it contains the following configuration, and update the credentials (username and password) with your own.

# MySQL Database Connection
spring.datasource.url=jdbc:mysql://localhost:3306/expedia_clone_db
spring.datasource.username=expedia_app
spring.datasource.password=your_secure_password

# JPA & Hibernate Configuration
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Optional: Set the logging level to DEBUG to see detailed logs
logging.level.com.expediaclon.backend=DEBUG

## ▶️ How to Run the Application

1.  Open the project in your IDE (IntelliJ IDEA is recommended).
2.  Wait for Maven to download all the project dependencies.
3.  Navigate to the `src/main/kotlin/com/expediaclon/backend/BackendApplication.kt` file.
4.  Run the `main` function. The application will start on `http://localhost:8080`.

The first time you run the application, Hibernate will automatically create all the necessary tables in the `expedia_clone_db` database, and the `DataLoader` will populate it with sample data (hotels and rooms).

---

## 📡 API Endpoints

The following endpoints are available in the current version.

### Hotels

* **Search Hotels**
    * `GET /api/hotels`
    * **Description:** Searches for available hotels in a city for a given number of passengers.
    * **Query Parameters:** `city` (String), `passengerCount` (Int).
    * **Example:** `GET http://localhost:8080/api/hotels?city=Paris&passengerCount=2`

* **Get Hotel Details**
    * `GET /api/hotels/{hotelId}`
    * **Description:** Returns the details of a specific hotel, including a list of its available room types.
    * **Example:** `GET http://localhost:8080/api/hotels/1`

### Bookings

* **Create a Booking**
    * `POST /api/bookings`
    * **Description:** Creates a new booking for a room type. The payment logic is simulated (the booking is confirmed automatically).
    * **Request Body (Example):**
        ```json
        {
            "roomId": 3007,
            "checkInDate": "2025-11-20",
            "checkOutDate": "2025-11-25",
            "totalGuests": 2,
            "guestNames": "Jonathan Smith, Angel Smith"
        }
        ```

* **Get All Bookings**
    * `GET /api/bookings`
    * **Description:** Returns a list of all created bookings. (Currently does not require authentication).
    * **Example:** `GET http://localhost:8080/api/bookings`

* **Get Booking Details**
    * `GET /api/bookings/{bookingId}`
    * **Description:** Returns the detailed information for a specific booking.
    * **Example:** `GET http://localhost:8080/api/bookings/1`

* **Cancel a Booking**
    * `POST /api/bookings/{bookingId}/cancel`
    * **Description:** Changes a booking's status to `CANCELLED`.
    * **Example:** `POST http://localhost:8080/api/bookings/1/cancel`
