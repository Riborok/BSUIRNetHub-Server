package com.bsuirnethub.model

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.entity.UserChatEntity

class UserChat(
    var chatId: Long?,
    var userId: UserId?,
    var unreadMessages: Int
)

fun UserChatEntity.toModel(): UserChat {
    return UserChat(
        chat?.id,
        user?.userId,
        unreadMessages
    )
}

fun Collection<UserChatEntity>.toModels(): List<UserChat> {
    return this.map { it.toModel() }
}