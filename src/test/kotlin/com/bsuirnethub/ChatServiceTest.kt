package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.extension.combinations
import com.bsuirnethub.service.ChatService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatServiceTest(
    @Autowired val chatService: ChatService,
    @Autowired val userService: UserService,
    @Autowired val userInitializer: UserInitializer,
    @Autowired private val databaseCleanup: DatabaseCleanup
) {
    @BeforeEach
    fun setUp() {
        databaseCleanup.clearDatabase()
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.clearDatabase()
    }

    @Test
    fun `test createUniqueChat`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertEquals(2, chat.userChats!!.size)
    }

    @Test
    fun `test createUniqueChat When Chat Already Exists`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        }
        assertThrows<RestStatusException> {
            chatService.createUniqueChat(listOf(userIds[1], userIds[0]))
        }
    }

    @Test
    fun `test createUniqueChat With Duplicates Users`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        assertThrows<RestStatusException> {
            chatService.createUniqueChat(listOf(userIds[0], userIds[1], userIds[0]))
        }
    }

    @Test
    fun `test deleteChat`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        chatService.deleteChat(userIds[0], chat.id!!)
        assertEquals(0, databaseCleanup.chatRepository.count())
        assertEquals(0, databaseCleanup.userChatRepository.count())
        assertEquals(2, databaseCleanup.userRepository.count())
    }

    @Test
    fun `test deleteChat With Non-Existent Id`() {
        val userId = "user1"
        val chatId = 1L
        assertThrows<RestStatusException> {
            chatService.deleteChat(userId, chatId)
        }
    }

    @Test
    fun `test deleteChat When senderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0]))
        assertThrows<RestStatusException> {
            chatService.deleteChat(userIds[1], chat.id!!)
        }
    }

    @Test
    fun `test getUniqueChat With Various Combinations Of Participants`() {
        val userIds = userInitializer.createAndSaveUsers(6).userIds
        val allCombinations = userIds.combinations().filter { it.isNotEmpty() }
        val chatMap = allCombinations.associateWith { chatService.createUniqueChat(it) }
        chatMap.forEach { (participantIds, chat) ->
            assertEquals(chat.id, chatService.getUniqueChat(participantIds[0], participantIds).id)
        }
    }

    @Test
    fun `test getUniqueChat When SenderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        chatService.createUniqueChat(listOf(userIds[0]))
        assertThrows<RestStatusException> {
            chatService.getUniqueChat(userIds[1], listOf(userIds[0]))
        }
    }

    @Test
    fun `test getChat With Various Combinations Of Participants`() {
        val userIds = userInitializer.createAndSaveUsers(6).userIds
        val allCombinations = userIds.combinations().filter { it.isNotEmpty() }
        val chatMap = allCombinations.associateWith { chatService.createUniqueChat(it) }
        chatMap.forEach { (participantIds, chat) ->
            val foundParticipants = chatService.getChat(participantIds[0], chat.id!!).userChats?.map { it.userId!! }!!
            assertEquals(participantIds.sorted(), foundParticipants.sorted())
        }
    }

    @Test
    fun `test getChat When SenderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0]))
        assertThrows<RestStatusException> {
            chatService.getChat(userIds[1], chat.id!!)
        }
    }

    @Test
    fun `test getChats`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat1 = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        val chat2 = chatService.createUniqueChat(listOf(userIds[0]))
        val chat3 = chatService.createUniqueChat(listOf(userIds[1]))
        val chatIds1 = chatService.getChats(userIds[0]).map { it?.id }
        val chatIds2 = chatService.getChats(userIds[1]).map { it?.id }
        assertEquals(2, chatIds1.size)
        assertEquals(2, chatIds2.size)
        assertTrue(chatIds1.contains(chat1.id))
        assertTrue(chatIds1.contains(chat2.id))
        assertTrue(chatIds2.contains(chat1.id))
        assertTrue(chatIds2.contains(chat3.id))
    }

    @Test
    fun `test Chats After Deleting User`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        val allCombinations = userIds.combinations().filter { it.isNotEmpty() }
        allCombinations.forEach { chatService.createUniqueChat(it) }
        userIds.forEach { userService.deleteUser(it) }
        assertEquals(0, databaseCleanup.chatRepository.count())
    }
}
