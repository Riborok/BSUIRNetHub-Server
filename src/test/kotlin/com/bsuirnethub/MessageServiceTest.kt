package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.service.ChatService
import com.bsuirnethub.service.MessageService
import com.bsuirnethub.service.UserChatService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MessageServiceTest(
    @Autowired val chatService: ChatService,
    @Autowired val messageService: MessageService,
    @Autowired val userChatService: UserChatService,
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

    private val content = "Hello, world!"

    @Test
    fun `test saveMessage`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        val chat = chatService.createUniqueChat(listOf(userId))
        val savedMessage = messageService.saveMessage(userId, chat.id!!, content)
        assertEquals(content, savedMessage.content)
    }

    @Test
    fun `test saveMessage When senderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            messageService.saveMessage(userIds[2], chat.id!!, content)
        }
    }

    @Test
    fun `test getUnreadMessages`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        val contents = (0 until 42).map { "$content$it" }
        contents.forEach { messageService.saveMessage(userIds[1], chat.id!!, it) }
        userChatService.incrementUnreadMessagesForRecipients(userIds[1], chat.id!!, 42)
        val unreadMessages = messageService.getUnreadMessages(userIds[0], chat.id!!)
        assertEquals(contents.reversed(), unreadMessages.map { it.content })
    }

    @Test
    fun `test getUnreadMessages When senderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            messageService.getUnreadMessages(userIds[2], chat.id!!)
        }
    }

    @Test
    fun `test getUnreadMessages When UnreadMessages More Than Messages Count`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        val contents = (0 until 42).map { "$content$it" }
        contents.forEach { messageService.saveMessage(userIds[1], chat.id!!, it) }
        userChatService.incrementUnreadMessagesForRecipients(userIds[1], chat.id!!, 100)
        val unreadMessages = messageService.getUnreadMessages(userIds[0], chat.id!!)
        assertEquals(contents.reversed(), unreadMessages.map { it.content })
    }

    @Test
    fun `test getUnreadMessages When UnreadMessages Less Than Messages Count`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        val contents = (0 until 42).map { "$content$it" }
        contents.forEach { messageService.saveMessage(userIds[1], chat.id!!, it) }
        userChatService.incrementUnreadMessagesForRecipients(userIds[1], chat.id!!, 20)
        val unreadMessages = messageService.getUnreadMessages(userIds[0], chat.id!!)
        assertEquals(contents.reversed().take(20), unreadMessages.map { it.content })
    }

    @Test
    fun `test getMessagesPage`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        val contents = (0 until 42).map { "$content$it" }
        contents.forEach { messageService.saveMessage(userIds[1], chat.id!!, it) }
        for (pageSize in 1..42) {
            for (pageNumber in 0..(42 / pageSize)) {
                val messages = messageService.getMessagesPage(userIds[0], chat.id!!, pageNumber, pageSize)
                val startIndex = pageNumber * pageSize
                val endIndex = (pageNumber + 1) * pageSize
                val expectedContents = contents.reversed().slice(startIndex until (endIndex).coerceAtMost(contents.size))
                assertEquals(expectedContents, messages.map { it.content })
            }
        }
    }

    @Test
    fun `test getMessagesPage With Negative pageNumber`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            messageService.getMessagesPage(userIds[0], chat.id!!, -1, 12)
        }
    }

    @Test
    fun `test getMessagesPage With Zero pageSize`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            messageService.getMessagesPage(userIds[0], chat.id!!, 1, 0)
        }
    }

    @Test
    fun `test getMessagesPage When senderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            messageService.getMessagesPage(userIds[2], chat.id!!, 0, 10)
        }
    }

    @Test
    fun `test getMessages`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        val contents = (0 until 42).map { "$content$it" }
        contents.forEach { messageService.saveMessage(userIds[1], chat.id!!, it) }
        val messages = messageService.getMessages(userIds[1], chat.id!!)
        assertEquals(contents.reversed(), messages.map { it.content })
    }

    @Test
    fun `test getMessages When senderId Not In Participants`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        val chat = chatService.createUniqueChat(listOf(userIds[0], userIds[1]))
        assertThrows<RestStatusException> {
            messageService.getMessages(userIds[2], chat.id!!)
        }
    }
}