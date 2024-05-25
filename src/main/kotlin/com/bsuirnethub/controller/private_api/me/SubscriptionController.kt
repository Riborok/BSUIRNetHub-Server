package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.User
import com.bsuirnethub.service.SubscriptionService
import org.springframework.http.HttpStatus
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
        val myUserId = jwt.subject
        val updatedUser = subscriptionService.addSubscription(myUserId, subscriptionId)
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser)
    }

    @DeleteMapping("/subscriptions/{subscriptionId}")
    fun deleteSubscription(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable subscriptionId: UserId
    ): ResponseEntity<Any> {
        val myUserId = jwt.subject
        subscriptionService.deleteSubscription(myUserId, subscriptionId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/subscriptions")
    fun getSubscriptionIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<User>> {
        val myUserId = jwt.subject
        return ResponseEntity.ok(subscriptionService.getSubscriptionIds(myUserId))
    }

    @GetMapping("/subscribers")
    fun getSubscriberIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<User>> {
        val myUserId = jwt.subject
        return ResponseEntity.ok(subscriptionService.getSubscriberIds(myUserId))
    }
}
