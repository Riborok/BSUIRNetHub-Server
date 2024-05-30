package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class MessageErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    ErrorCode {
    INVALID_PAGE_REQUEST(ErrorCategories.INVALID_PAGE_REQUEST, "Invalid page request", HttpStatus.BAD_REQUEST);

    override val code: Int = ModelCodes.MESSAGE + errorCategory
}