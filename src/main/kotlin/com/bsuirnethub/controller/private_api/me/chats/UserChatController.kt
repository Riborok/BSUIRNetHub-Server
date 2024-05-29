package com.bsuirnethub.controller.private_api.me.chats

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.model.UserChat
import com.bsuirnethub.service.UserChatService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me/chats")
class UserChatController(private val userChatService: UserChatService) {
    @PutMapping("/{chatId}/read")
    fun markMessagesAsRead(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable chatId: Long,
        @RequestParam messageCount: Int
    ): ResponseEntity<UserChat> {
        val myUserId = jwt.subject
        val chat = userChatService.markMessagesAsRead(myUserId, chatId, messageCount)
        return ResponseEntity.ok(chat)
    }
}