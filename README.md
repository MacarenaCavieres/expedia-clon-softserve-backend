# Expedia Clone - Backend API

Este repositorio contiene el código fuente del backend para el proyecto de clon de Expedia. La API está construida con Kotlin y Spring Boot, y se encarga de gestionar itinerarios, hoteles, reservas y la lógica de negocio de la aplicación.

---

## 🚀 Tecnologías Utilizadas

* **Lenguaje:** Kotlin
* **Framework:** Spring Boot 3.x
* **Base de Datos:** MySQL
* **Acceso a Datos:** Spring Data JPA / Hibernate
* **Gestor de Dependencias:** Maven

---

## 📋 Prerrequisitos

Para poder ejecutar este proyecto en tu máquina local, necesitarás tener instalado lo siguiente:

* **Java JDK 17** o superior.
* **Apache Maven**.
* Un **servidor de MySQL** en ejecución.

---

## ⚙️ Configuración del Entorno Local

Sigue estos pasos para configurar tu base de datos y conectar la aplicación.

**1. Crear la Base de Datos**
Conéctate a tu servidor MySQL local y ejecuta el siguiente comando para crear la base de datos que usará la aplicación:
```sql
CREATE DATABASE expedia_clone_db;
```
**2. (Opcional pero Recomendado) Crear un Usuario Dedicado**
Por seguridad, es una buena práctica crear un usuario específico para la aplicación en lugar de usar root.
```sql
CREATE USER 'expedia_app'@'localhost' IDENTIFIED BY 'your_secure_password';
GRANT ALL PRIVILEGES ON expedia_clone_db.* TO 'expedia_app'@'localhost';
```

Recuerda reemplazar your_secure_password por una contraseña segura.

**3. Configurar la Aplicación**
En el directorio src/main/resources/, encuentra el archivo application.properties. 
Asegúrate de que contenga la siguiente configuración y actualiza las credenciales con las tuyas:


Conexión a la Base de Datos MySQL:
```
spring.datasource.url=jdbc:mysql://localhost:3306/expedia_clone_db
spring.datasource.username=expedia_app
spring.datasource.password=your_secure_password
```

Configuración de JPA & Hibernate:
```
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## ▶️ Cómo Ejecutar la Aplicación
Abre el proyecto en tu IDE (IntelliJ IDEA es recomendado).

Espera a que Maven descargue todas las dependencias.

Navega al archivo src/main/kotlin/com/expediaclon/backend/BackendApplication.kt.

Ejecuta la función main. La aplicación se iniciará en http://localhost:8080.

La primera vez que ejecutes la aplicación, Hibernate creará automáticamente todas las tablas en la base de datos expedia_clone_db, y el DataLoader poblará la base con los datos de muestra (hoteles y habitaciones).

## 📡 Endpoints de la API (MVP)
A continuación se detallan los endpoints disponibles en esta primera versión.

### Itinerarios:

- Crear Itinerario de Invitado:

```POST /api/itineraries```

Descripción: Crea un nuevo itinerario para un usuario no registrado.

```Respuesta Exitosa (200 OK):```

```JSON
{
  "id": 1,
  "sessionId": "a1b2c3d4-e5f6-7890-1234-567890abcdef"
} 
```

### Hoteles
- Buscar Hoteles

```GET /api/hotels```

Descripción: Busca hoteles disponibles en una ciudad para un número determinado de pasajeros.

Parámetros: ```city```(String), ```passengerCount``` (Int).

Ejemplo: ```GET http://localhost:8080/api/hotels?city=Paris&passengerCount=2```

Respuesta Exitosa ```(200 OK)```: Una lista de objetos ```Hotel```.

### Reservas (Próximamente)
- Crear una Reserva

```POST /api/bookings```

Descripción: Crea una nueva reserva para un tipo de habitación dentro de un itinerario. (Lógica de pago simulada).

Cuerpo de la Petición (Ejemplo):

```JSON
{
  "itineraryId": 1,
  "roomTypeId": 5,
  "checkInDate": "2025-11-20",
  "checkOutDate": "2025-11-25"
}
```