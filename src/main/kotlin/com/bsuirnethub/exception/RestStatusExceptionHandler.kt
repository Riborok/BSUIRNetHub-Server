package com.bsuirnethub.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestStatusExceptionHandler {
    @ExceptionHandler(RestStatusException::class)
    fun handleRestStatusException(ex: RestStatusException): ResponseEntity<String?> {
        return ResponseEntity.status(ex.status).body(ex.message)
    }
}
