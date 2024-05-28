package com.bsuirnethub

import com.bsuirnethub.entity.ChatEntity
import com.bsuirnethub.entity.UserChatEntity
import com.bsuirnethub.entity.UserEntity
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.ChatRepository
import com.bsuirnethub.repository.UserChatRepository
import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.ChatService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatServiceTest(
    @Autowired val userRepository: UserRepository,
    @Autowired val chatRepository: ChatRepository,
    @Autowired val userChatRepository: UserChatRepository,
    @Autowired val chatService: ChatService,
    @Autowired val userService: UserService,
) {
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
    fun `test createUniqueChat`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId1, userId2))
        assertEquals(2, chat.userChats!!.size)
    }

    @Test
    fun `test createUniqueChat When Chat Already Exists`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        chatService.createUniqueChat(listOf(userId1, userId2))
        assertThrows<RestStatusException> {
            chatService.createUniqueChat(listOf(userId1, userId2))
        }
        assertThrows<RestStatusException> {
            chatService.createUniqueChat(listOf(userId2, userId1))
        }
    }

    @Test
    fun `test createUniqueChat With Duplicates Users`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        assertThrows<RestStatusException> {
            chatService.createUniqueChat(listOf(userId1, userId2, userId1))
        }
    }

    @Test
    fun `test deleteChat`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat = chatService.createUniqueChat(listOf(userId1, userId2))
        chatService.deleteChat(userId1, chat.id!!)
        assertEquals(0, chatRepository.count())
        assertEquals(0, userChatRepository.count())
        assertEquals(2, userRepository.count())
    }

    @Test
    fun `test deleteChat with non-existent id`() {
        val userId = "user1"
        val chatId = 1L
        assertThrows<RestStatusException> {
            chatService.deleteChat(userId, chatId)
        }
    }

    @Test
    fun `test deleteChat When senderId Not In Participants`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        userRepository.save(user1)
        val chat = chatService.createUniqueChat(listOf(userId1))
        assertThrows<RestStatusException> {
            chatService.deleteChat(userId2, chat.id!!)
        }
    }

    @Test
    fun `test getUniqueChat With Various Combinations Of Participants`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val userId3 = "user3"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        val user3 = UserEntity(userId = userId3)
        userRepository.saveAll(listOf(user1, user2, user3))
        val chat1 = chatService.createUniqueChat(listOf(userId1, userId2))
        val chat2 = chatService.createUniqueChat(listOf(userId1, userId3))
        val chat3 = chatService.createUniqueChat(listOf(userId2, userId3))
        val chat4 = chatService.createUniqueChat(listOf(userId1, userId2, userId3))
        val chat5 = chatService.createUniqueChat(listOf(userId1))
        val chat6 = chatService.createUniqueChat(listOf(userId2))
        val chat7 = chatService.createUniqueChat(listOf(userId3))
        assertEquals(chat1.id, chatService.getUniqueChat(userId1, listOf(userId2, userId1)).id)
        assertEquals(chat2.id, chatService.getUniqueChat(userId1, listOf(userId3, userId1)).id)
        assertEquals(chat3.id, chatService.getUniqueChat(userId2, listOf(userId3, userId2)).id)
        assertEquals(chat4.id, chatService.getUniqueChat(userId1, listOf(userId3, userId2, userId1)).id)
        assertEquals(chat5.id, chatService.getUniqueChat(userId1, listOf(userId1)).id)
        assertEquals(chat6.id, chatService.getUniqueChat(userId2, listOf(userId2)).id)
        assertEquals(chat7.id, chatService.getUniqueChat(userId3, listOf(userId3)).id)
    }

    @Test
    fun `test getUniqueChat When SenderId Not In Participants`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        userRepository.save(user1)
        chatService.createUniqueChat(listOf(userId1))
        assertThrows<RestStatusException> {
            chatService.getUniqueChat(userId2, listOf(userId1))
        }
    }

    @Test
    fun `test getUniqueChat When Chat Not Unique`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chatEntity1 = ChatEntity().apply {
            userChats.addAll(listOf(UserChatEntity(user = user1, chat = this), UserChatEntity(user = user2, chat = this)))
        }
        val chatEntity2 = ChatEntity().apply {
            userChats.addAll(listOf(UserChatEntity(user = user1, chat = this), UserChatEntity(user = user2, chat = this)))
        }
        chatRepository.saveAll(listOf(chatEntity1, chatEntity2))
        assertThrows<RestStatusException> {
            chatService.getUniqueChat(userId2, listOf(userId1, userId2))
        }
    }

    @Test
    fun `test getChats`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        userRepository.saveAll(listOf(user1, user2))
        val chat1 = chatService.createUniqueChat(listOf(userId1, userId2))
        val chat2 = chatService.createUniqueChat(listOf(userId1))
        val chat3 = chatService.createUniqueChat(listOf(userId2))
        val chatIds1 = chatService.getChats(userId1).map { it?.id }
        val chatIds2 = chatService.getChats(userId2).map { it?.id }
        assertEquals(2, chatIds1.size)
        assertEquals(2, chatIds2.size)
        assertTrue(chatIds1.contains(chat1.id))
        assertTrue(chatIds1.contains(chat2.id))
        assertTrue(chatIds2.contains(chat1.id))
        assertTrue(chatIds2.contains(chat3.id))
    }

    @Test
    fun `test Chat After Deleting User`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val userId3 = "user3"
        val user1 = UserEntity(userId = userId1)
        val user2 = UserEntity(userId = userId2)
        val user3 = UserEntity(userId = userId3)
        userRepository.saveAll(listOf(user1, user2, user3))
        chatService.createUniqueChat(listOf(userId1, userId2))
        chatService.createUniqueChat(listOf(userId1, userId3))
        chatService.createUniqueChat(listOf(userId2, userId3))
        chatService.createUniqueChat(listOf(userId1, userId2, userId3))
        chatService.createUniqueChat(listOf(userId1))
        chatService.createUniqueChat(listOf(userId2))
        chatService.createUniqueChat(listOf(userId3))
        userService.deleteUser(userId1)
        userService.deleteUser(userId2)
        userService.deleteUser(userId3)
        assertEquals(0, userChatRepository.count())
        assertEquals(0, chatRepository.count())
    }
}
