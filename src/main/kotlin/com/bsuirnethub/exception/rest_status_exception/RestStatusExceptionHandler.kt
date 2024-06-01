package com.bsuirnethub.exception.rest_status_exception

import com.bsuirnethub.exception.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestStatusExceptionHandler {
    @ExceptionHandler(RestStatusException::class)
    fun handleRestStatusException(ex: RestStatusException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse(code = ex.restErrorCode.code, message = ex.restErrorCode.message, source = ex.source)
        return ResponseEntity.status(ex.restErrorCode.status).body(response)
    }
}
