package com.bsuirnethub.validator

import com.bsuirnethub.exception.rest_status_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.MessageRestErrorCode
import org.springframework.stereotype.Component

@Component
class MessageValidator {
    fun validatePageRequest(pageNumber: Int, pageSize: Int) {
        when {
            pageNumber < 0 && pageSize <= 0 ->
                throw RestStatusException(MessageRestErrorCode.INVALID_PAGE_REQUEST, pageNumber to pageSize)
            pageNumber < 0 ->
                throw RestStatusException(MessageRestErrorCode.INVALID_PAGE_REQUEST, pageNumber)
            pageSize <= 0 ->
                throw RestStatusException(MessageRestErrorCode.INVALID_PAGE_REQUEST, pageSize)
        }
    }
}