package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.Chat
import com.bsuirnethub.service.ChatService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me/chats")
class ChatController(private val chatService: ChatService) {
    @PostMapping("/with/{userId}")
    fun createChat(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable userId: UserId
    ): ResponseEntity<Chat> {
        val myUserId = jwt.subject
        val chat = chatService.createUniqueChat(listOf(myUserId, userId))
        return ResponseEntity.status(HttpStatus.CREATED).body(chat)
    }

    @GetMapping("/with/{userId}")
    fun getUniqueChat(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable userId: UserId
    ): ResponseEntity<Chat> {
        val myUserId = jwt.subject
        val chat = chatService.getUniqueChat(myUserId, listOf(myUserId, userId))
        return ResponseEntity.ok(chat)
    }

    @DeleteMapping("/{chatId}")
    fun deleteChat(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable chatId: Long
    ): ResponseEntity<Unit> {
        val myUserId = jwt.subject
        chatService.deleteChat(myUserId, chatId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getChats(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<Chat?>> {
        val myUserId = jwt.subject
        val chat = chatService.getChats(myUserId)
        return ResponseEntity.ok(chat)
    }
}