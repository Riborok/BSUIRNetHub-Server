package com.bsuirnethub.repository

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.MessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable

@Repository
interface MessageRepository : JpaRepository<MessageEntity, Long> {
    fun findByChatOrderByTimestampDesc(chat: ChatEntity, pageable: Pageable): List<MessageEntity>
}