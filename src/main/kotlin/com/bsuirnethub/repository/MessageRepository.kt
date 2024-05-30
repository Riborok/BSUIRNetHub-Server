package com.bsuirnethub.repository

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.MessageEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<MessageEntity, Long> {
    @Query("SELECT m FROM MessageEntity m WHERE m.chat = :chat ORDER BY m.timestamp DESC LIMIT :limit")
    fun findFirstNByChatOrderByTimestampDesc(chat: ChatEntity, limit: Int): List<MessageEntity>
    fun findByChatOrderByTimestampDesc(chat: ChatEntity, pageable: Pageable): List<MessageEntity>
}
