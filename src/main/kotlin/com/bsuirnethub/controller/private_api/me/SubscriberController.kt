package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.service.SubscriberService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me/subscribers")
class SubscriberController(private val subscriberService: SubscriberService) {
    @GetMapping
    fun getSubscriberIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<UserId?>> {
        val myUserId = jwt.subject
        return ResponseEntity.ok(subscriberService.getSubscriberIds(myUserId))
    }
}