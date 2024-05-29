package com.bsuirnethub

import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.ChatRepository
import com.bsuirnethub.repository.UserChatRepository
import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.ChatService
import com.bsuirnethub.service.UserChatService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserChatServiceTest (
    @Autowired val userRepository: UserRepository,
    @Autowired val chatRepository: ChatRepository,
    @Autowired val userChatRepository: UserChatRepository,
    @Autowired val chatService: ChatService,
    @Autowired val userChatService: UserChatService,
){
    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        chatRepository.deleteAll()
        userChatRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
        chatRepository.deleteAll()
        userChatRepository.deleteAll()
    }

    @Test
    fun `test incrementUnreadMessagesForRecipients`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val userId3 = "user3"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        val user3 = UserEntity(userId = userId3)
        userRepository.saveAll(listOf(user1, user2, user3))
        var chat = chatService.createUniqueChat(listOf(userId1, userId2, userId3))
        chat = userChatService.incrementUnreadMessagesForRecipients(userId1, chat.id!!, 10)
        assertEquals(0, chat.userChats?.find { it.userId == userId1 }?.unreadMessages)
        assertEquals(10, chat.userChats?.find { it.userId == userId2 }?.unreadMessages)
        assertEquals(10, chat.userChats?.find { it.userId == userId3 }?.unreadMessages)
    }

    @Test
    fun `test incrementUnreadMessagesForRecipients When Sender Not In Participants`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        userRepository.save(user1)
        val chat = chatService.createUniqueChat(listOf(userId1))
        assertThrows<RestStatusException> {
            userChatService.incrementUnreadMessagesForRecipients(userId2, chat.id!!)
        }
    }

    @Test
    fun `test incrementUnreadMessagesForRecipients When Message Count Is Negative`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId1, userId2))
        assertThrows<RestStatusException> {
            userChatService.incrementUnreadMessagesForRecipients(userId1, chat.id!!, -1)
        }
    }

    @Test
    fun `test markMessagesAsRead`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId1, userId2))
        userChatService.incrementUnreadMessagesForRecipients(userId1, chat.id!!, 10)
        val userChat = userChatService.markMessagesAsRead(userId2, chat.id!!, 5)
        assertEquals(5, userChat.unreadMessages)
    }

    @Test
    fun `test markMessagesAsRead when readCount exceeds unreadCount`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId1, userId2))
        userChatService.incrementUnreadMessagesForRecipients(userId1, chat.id!!, 10)
        val userChat = userChatService.markMessagesAsRead(userId2, chat.id!!, 55)
        assertEquals(0, userChat.unreadMessages)
    }

    @Test
    fun `test markMessagesAsRead With Negative Message Count`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId1, userId2))
        assertThrows<RestStatusException> {
            userChatService.markMessagesAsRead(userId1, chat.id!!, -1)
        }
    }

    @Test
    fun `test markMessagesAsRead When Sender Not In Participants`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId2))
        assertThrows<RestStatusException> {
            userChatService.markMessagesAsRead(userId1, chat.id!!, 10)
        }
    }
}