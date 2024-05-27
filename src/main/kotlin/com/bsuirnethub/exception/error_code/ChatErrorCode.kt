package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class ChatErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    ErrorCode {
    CHAT_NOT_FOUND(ErrorCategories.NOT_FOUND, "Chat not found", HttpStatus.NOT_FOUND),
    CHAT_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "Chat already exists", HttpStatus.CONFLICT),
    MULTIPLE_CHATS_FOUND(ErrorCategories.MULTIPLE_FOUND, "Multiple chats found", HttpStatus.CONFLICT),
    SENDER_NOT_FOUND_IN_PARTICIPANTS(ErrorCategories.OTHER, "Sender not found in participants", HttpStatus.BAD_REQUEST);

    override val code: Int = ModelCodes.CHAT + errorCategory
}
