package com.expediaclon.backend.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

// @ControllerAdvice le dice a Spring que esta clase es una "red de seguridad"
// para todos los controladores. Escuchará las excepciones que ocurran.
@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // @ExceptionHandler le dice a este método que se active
    // CADA VEZ que un controlador lance una IllegalArgumentException.
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<ErrorDetails> {
        // Registramos el error en el log del servidor para tener un registro.
        logger.warn("Invalid request: ${ex.message}")

        // Creamos un objeto de error limpio para enviar al cliente.
        val errorDetails = ErrorDetails(
            message = ex.message ?: "Invalid argument",
            details = request.getDescription(false)
        )

        // Devolvemos un código de error 400 Bad Request, que es el correcto
        // para una petición mal formada por parte del cliente.
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }
}

// Una clase simple para darle formato a nuestra respuesta de error.
data class ErrorDetails(val message: String, val details: String)