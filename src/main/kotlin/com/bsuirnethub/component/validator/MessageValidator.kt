package com.bsuirnethub.component.validator

import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.MessageErrorCode
import org.springframework.stereotype.Component

@Component
class MessageValidator {
    fun validatePageRequest(pageNumber: Int, pageSize: Int) {
        when {
            pageNumber < 0 && pageSize <= 0 ->
                throw RestStatusException(MessageErrorCode.INVALID_PAGE_REQUEST, pageNumber to pageSize)
            pageNumber < 0 ->
                throw RestStatusException(MessageErrorCode.INVALID_PAGE_REQUEST, pageNumber)
            pageSize <= 0 ->
                throw RestStatusException(MessageErrorCode.INVALID_PAGE_REQUEST, pageSize)
        }
    }
}