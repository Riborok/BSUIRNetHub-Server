package com.bsuirnethub.model

import com.bsuirnethub.entity.UserChatEntity
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserChat(
    var user: User?,
    var chat: Chat?,
    var unreadMessages: Int
)

fun UserChatEntity.toModel(): UserChat {
    return UserChat(
        user?.toUserId(),
        chat?.toChatInfo(),
        unreadMessages
    )
}

fun Collection<UserChatEntity>.toModels(): List<UserChat> {
    return this.map { it.toModel() }
}