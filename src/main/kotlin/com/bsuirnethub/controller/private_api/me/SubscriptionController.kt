package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.User
import com.bsuirnethub.service.SubscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me")
class SubscriptionController(private val subscriptionService: SubscriptionService) {
    @PostMapping("/subscriptions/{subscriptionId}")
    fun addSubscription(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable subscriptionId: UserId
    ): ResponseEntity<User> {
        val userId = jwt.subject
        return ResponseEntity.ok(subscriptionService.addSubscription(userId, subscriptionId))
    }

    @DeleteMapping("/subscriptions/{subscriptionId}")
    fun deleteSubscription(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable subscriptionId: UserId
    ): ResponseEntity<Any> {
        val userId = jwt.subject
        subscriptionService.deleteSubscription(userId, subscriptionId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/subscriptions")
    fun getSubscriptionIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<User>> {
        val userId = jwt.subject
        return ResponseEntity.ok(subscriptionService.getSubscriptionIds(userId))
    }

    @GetMapping("/subscribers")
    fun getSubscriberIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<User>> {
        val userId = jwt.subject
        return ResponseEntity.ok(subscriptionService.getSubscriberIds(userId))
    }
}
