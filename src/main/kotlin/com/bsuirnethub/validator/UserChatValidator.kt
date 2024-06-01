package com.bsuirnethub.validator

import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.UserChatRestErrorCode
import org.springframework.stereotype.Component

@Component
class UserChatValidator {
    fun validateUserChatExist(userChatEntity: UserChatEntity?, chatId: Long?): UserChatEntity {
        return userChatEntity ?: throw RestStatusException(UserChatRestErrorCode.USER_CHAT_NOT_FOUND, chatId)
    }

    fun validateMessageCountNonNegative(messageCount: Int) {
        if (messageCount < 0)
            throw RestStatusException(UserChatRestErrorCode.INVALID_MESSAGE_COUNT, messageCount)
    }
}