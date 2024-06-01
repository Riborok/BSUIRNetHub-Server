package com.bsuirnethub.exception.error_code.rest

import com.bsuirnethub.exception.error_code.ErrorCategories
import com.bsuirnethub.exception.error_code.ModelCodes
import org.springframework.http.HttpStatus

enum class ChatRestErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    RestErrorCode {
    CHAT_NOT_FOUND(ErrorCategories.NOT_FOUND, "Chat not found", HttpStatus.NOT_FOUND),
    CHAT_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "Chat already exists", HttpStatus.CONFLICT),
    MULTIPLE_CHATS_FOUND(ErrorCategories.MULTIPLE_FOUND, "Multiple chats found", HttpStatus.CONFLICT),
    DUPLICATE_PARTICIPANTS(ErrorCategories.DUPLICATES_IN_REQUEST, "Participants list contains duplicates", HttpStatus.BAD_REQUEST),
    SENDER_NOT_FOUND_IN_PARTICIPANTS(ErrorCategories.OTHER, "Sender not found in participants", HttpStatus.BAD_REQUEST);

    override val code: Int = ModelCodes.CHAT + errorCategory
}
