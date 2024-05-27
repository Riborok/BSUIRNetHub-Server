package com.bsuirnethub.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestStatusExceptionHandler {
    @ExceptionHandler(RestStatusException::class)
    fun handleRestStatusException(ex: RestStatusException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(code = ex.errorCode.code, message = ex.errorCode.message, source = ex.source)
        return ResponseEntity.status(ex.errorCode.status).body(response)
    }
}
