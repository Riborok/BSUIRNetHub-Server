package com.bsuirnethub.validator

import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.RestErrorCode
import org.springframework.dao.DataIntegrityViolationException

internal fun<T> validateEntityDoesNotExists(operation: () -> T, restErrorCode: RestErrorCode, source: Any): T {
    return try {
        operation()
    } catch (e: DataIntegrityViolationException) {
        throw RestStatusException(restErrorCode, source)
    }
}
