package com.bsuirnethub.component

import com.bsuirnethub.repository.*
import org.springframework.stereotype.Component

@Component
class DatabaseCleanup(
    val chatRepository: ChatRepository,
    val groupRepository: GroupRepository,
    val messageRepository: MessageRepository,
    val subscriptionRepository: SubscriptionRepository,
    val teacherRepository: TeacherRepository,
    val userChatRepository: UserChatRepository,
    val userRepository: UserRepository,
) {
    fun clearDatabase() {
        listOf(
            chatRepository, groupRepository, messageRepository, subscriptionRepository,
            teacherRepository, userChatRepository, userRepository
        ).forEach { it.deleteAll() }
    }
}