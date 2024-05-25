package com.bsuirnethub.repository

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserChatRepository : JpaRepository<UserChatEntity, Long> {
    fun findByUserAndChat(user: UserEntity, chat: ChatEntity): UserChatEntity
}
