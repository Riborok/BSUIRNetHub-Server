package com.bsuirnethub.component.validator

import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.ErrorCode
import org.springframework.dao.DataIntegrityViolationException

internal fun<T> validateEntityDoesNotExists(operation: () -> T, errorCode: ErrorCode, source: Any): T {
    return try {
        operation()
    } catch (e: DataIntegrityViolationException) {
        throw RestStatusException(errorCode, source)
    }
}
