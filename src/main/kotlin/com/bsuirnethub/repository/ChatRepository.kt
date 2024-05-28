package com.bsuirnethub.repository

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : JpaRepository<ChatEntity, Long> {
    @Query("""
        SELECT c
        FROM ChatEntity c
        JOIN UserChatEntity uc ON c.id = uc.chat.id
        WHERE uc.user IN :participants
        GROUP BY c.id
        HAVING COUNT(uc.user) = :size AND
               COUNT(uc.user) = (SELECT COUNT(uc2.user) FROM UserChatEntity uc2 WHERE uc2.chat = c)
    """)
    fun findByParticipants(@Param("participants") participants: List<UserEntity>, @Param("size") size: Long): List<ChatEntity>
}