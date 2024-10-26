package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.extension.combinations
import com.bsuirnethub.service.ChatService
import com.bsuirnethub.service.UserChatService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserChatServiceTest (
    @Autowired val chatService: ChatService,
    @Autowired val userChatService: UserChatService,
    @Autowired val userService: UserService,
    @Autowired val userInitializer: UserInitializer,
    @Autowired private val databaseCleanup: DatabaseCleanup
){
    @BeforeEach
    fun setUp() {
        databaseCleanup.clearDatabase()
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.clearDatabase()
    }

    @Test
    fun `test incrementUnreadMessagesForRecipients`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        var chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1], userIds[2]))
        chat = userChatService.incrementUnreadMessagesForRecipients(userIds[0], chat.id!!, 10)
        assertEquals(0, chat.userChats?.find { it.userId == userIds[0] }?.unreadMessages)
        assertEquals(10, chat.userChats?.find { it.userId == userIds[1] }?.unreadMessages)
        assertEquals(10, chat.userChats?.find { it.userId == userIds[2] }?.unreadMessages)
    }

    @Test
    fun `test incrementUnreadMessagesForRecipients When Sender Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0]))
        assertThrows<RestStatusException> {
            userChatService.incrementUnreadMessagesForRecipients(userIds[1], chat.id!!)
        }
    }

    @Test
    fun `test incrementUnreadMessagesForRecipients When Message Count Is Negative`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            userChatService.incrementUnreadMessagesForRecipients(userIds[0], chat.id!!, -1)
        }
    }

    @Test
    fun `test markMessagesAsRead`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        var chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        userChatService.incrementUnreadMessagesForRecipients(userIds[0], chat.id!!, 10)
        chat = userChatService.markMessagesAsRead(userIds[1], chat.id!!, 5)
        val userChat = chat.userChats?.find { it.userId == userIds[1] }
        assertEquals(5, userChat?.unreadMessages)
    }

    @Test
    fun `test markMessagesAsRead When readCount Exceeds unreadCount`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        var chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        userChatService.incrementUnreadMessagesForRecipients(userIds[0], chat.id!!, 10)
        chat = userChatService.markMessagesAsRead(userIds[1], chat.id!!, 55)
        val userChat = chat.userChats?.find { it.userId == userIds[1] }
        assertEquals(0, userChat?.unreadMessages)
    }

    @Test
    fun `test markMessagesAsRead With Negative Message Count`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            userChatService.markMessagesAsRead(userIds[0], chat.id!!, -1)
        }
    }

    @Test
    fun `test markMessagesAsRead When Sender Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[1]))
        assertThrows<RestStatusException> {
            userChatService.markMessagesAsRead(userIds[0], chat.id!!, 10)
        }
    }

    @Test
    fun `test User Chats After Deleting User`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        val allCombinations = userIds.combinations().filter { it.isNotEmpty() }
        allCombinations.forEach { chatService.createUniqueChat(it) }
        userIds.forEach { userService.deleteUser(it) }
        assertEquals(0, databaseCleanup.userChatRepository.count())
    }
}