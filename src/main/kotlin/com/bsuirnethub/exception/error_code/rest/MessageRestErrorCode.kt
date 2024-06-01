package com.bsuirnethub.exception.error_code.rest

import com.bsuirnethub.exception.error_code.constant.ErrorCategories
import com.bsuirnethub.exception.error_code.constant.ModelCodes
import org.springframework.http.HttpStatus

enum class MessageRestErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    RestErrorCode {
    INVALID_PAGE_REQUEST(ErrorCategories.INVALID_PAGE_REQUEST, "Invalid page request", HttpStatus.BAD_REQUEST);

    override val code: Int = ModelCodes.MESSAGE + errorCategory
}