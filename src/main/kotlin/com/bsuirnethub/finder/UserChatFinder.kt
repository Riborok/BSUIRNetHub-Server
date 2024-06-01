package com.bsuirnethub.finder

import com.bsuirnethub.validator.UserChatValidator
import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.repository.UserChatRepository
import org.springframework.stereotype.Component

@Component
class UserChatFinder(
    private val userChatRepository: UserChatRepository,
    private val userChatValidator: UserChatValidator
) {
    fun findUserChatEntityByUserEntityAndChatEntityOrThrow(user: UserEntity, chat: ChatEntity): UserChatEntity {
        return userChatValidator.validateUserChatExist(userChatRepository.findByUserAndChat(user, chat), chat.id)
    }
}