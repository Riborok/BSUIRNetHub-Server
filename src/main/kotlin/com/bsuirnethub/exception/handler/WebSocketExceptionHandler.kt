package com.bsuirnethub.exception.handler

import com.bsuirnethub.exception.response.ErrorResponse
import com.bsuirnethub.exception.error_code_exception.ErrorCodeException
import org.springframework.stereotype.Component

@Component
class WebSocketExceptionHandler {
    fun handleErrorCodeException(ex: ErrorCodeException): ErrorResponse {
        val response = ErrorResponse(code = ex.errorCode.code, message = ex.errorCode.message, source = ex.source)
        return response
    }
}
