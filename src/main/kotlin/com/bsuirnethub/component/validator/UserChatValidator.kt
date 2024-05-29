package com.bsuirnethub.component.validator

import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.UserChatErrorCode
import org.springframework.stereotype.Component

@Component
class UserChatValidator {
    fun validateUserChatExist(userChatEntity: UserChatEntity?, chatId: Long?): UserChatEntity {
        return userChatEntity ?: throw RestStatusException(UserChatErrorCode.USER_CHAT_NOT_FOUND, chatId)
    }

    fun validateMessageCountNonNegative(messageCount: Int) {
        if (messageCount < 0)
            throw RestStatusException(UserChatErrorCode.INVALID_MESSAGE_COUNT, messageCount)
    }
}