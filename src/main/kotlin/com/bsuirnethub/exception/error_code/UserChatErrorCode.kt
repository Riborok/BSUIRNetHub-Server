package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class UserChatErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus):
        ErrorCode {
    USER_CHAT_NOT_FOUND(ErrorCategories.NOT_FOUND, "User chat not found", HttpStatus.NOT_FOUND),
    INVALID_MESSAGE_COUNT(ErrorCategories.INVALID_NEGATIVE_VALUE_IN_REQUEST, "Message count cannot be negative", HttpStatus.BAD_REQUEST);

    override val code: Int = ModelCodes.USER_CHAT + errorCategory
}