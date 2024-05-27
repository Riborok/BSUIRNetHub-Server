package com.bsuirnethub.component.validator

import com.bsuirnethub.exception.RestStatusException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus

internal fun<T> validateEntityDoesNotExists(operation: () -> T, errorMessage: String): T {
    return try {
        operation()
    } catch (e: DataIntegrityViolationException) {
        throw RestStatusException(errorMessage, HttpStatus.CONFLICT)
    }
}