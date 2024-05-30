package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.model.Message
import com.bsuirnethub.service.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me/messages")
class MessageController(private val messageService: MessageService) {
    @GetMapping("/{chatId}")
    fun getAllMessages(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable chatId: Long
    ): ResponseEntity<List<Message>> {
        val senderId = jwt.subject
        val allMessages = messageService.getMessages(senderId, chatId)
        return ResponseEntity.ok(allMessages)
    }

    @GetMapping("/{chatId}/unread")
    fun getUnreadMessages(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable chatId: Long
    ): ResponseEntity<List<Message>> {
        val senderId = jwt.subject
        val unreadMessages = messageService.getUnreadMessages(senderId, chatId)
        return ResponseEntity.ok(unreadMessages)
    }

    @GetMapping("/{chatId}/page")
    fun getMessagesPage(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable chatId: Long,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int
    ): ResponseEntity<List<Message>> {
        val senderId = jwt.subject
        val messagesPage = messageService.getMessagesPage(senderId, chatId, pageNumber, pageSize)
        return ResponseEntity.ok(messagesPage)
    }
}