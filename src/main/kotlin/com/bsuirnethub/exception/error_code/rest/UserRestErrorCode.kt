package com.bsuirnethub.exception.error_code.rest

import com.bsuirnethub.exception.error_code.ErrorCategories
import com.bsuirnethub.exception.error_code.ModelCodes
import org.springframework.http.HttpStatus

enum class UserRestErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    RestErrorCode {
    USER_NOT_FOUND(ErrorCategories.NOT_FOUND, "User not found", HttpStatus.NOT_FOUND),
    USERS_NOT_FOUND(ErrorCategories.NOT_FOUND_GROUP, "Users not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "User already exists", HttpStatus.CONFLICT);

    override val code: Int = ModelCodes.USER + errorCategory
}
