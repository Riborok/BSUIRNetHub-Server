package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class UserErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    ErrorCode {
    USER_NOT_FOUND(ErrorCategories.NOT_FOUND, "User not found", HttpStatus.NOT_FOUND),
    USERS_NOT_FOUND(ErrorCategories.NOT_FOUND_GROUP, "Users not found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "User already exists", HttpStatus.CONFLICT);

    override val code: Int = ModelCodes.USER + errorCategory
}
