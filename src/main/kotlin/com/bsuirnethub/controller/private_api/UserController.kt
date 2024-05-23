package com.bsuirnethub.controller.private_api

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.User
import com.bsuirnethub.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/users")
class UserController(private val userService: UserService) {
    @PostMapping("/me/subscriptions/{subscriptionId}")
    fun addSubscription(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable subscriptionId: UserId
    ): ResponseEntity<User> {
        val userId = jwt.subject
        return ResponseEntity.ok(userService.addSubscription(userId, subscriptionId))
    }

    @DeleteMapping("/me/subscriptions/{subscriptionId}")
    fun deleteSubscription(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable subscriptionId: UserId
    ): ResponseEntity<Any> {
        val userId = jwt.subject
        userService.deleteSubscription(userId, subscriptionId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/me/subscriptions")
    fun getSubscriptionIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<User>> {
        val userId = jwt.subject
        return ResponseEntity.ok(userService.getSubscriptionIds(userId))
    }

    @GetMapping("/me/subscribers")
    fun getSubscriberIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<User>> {
        val userId = jwt.subject
        return ResponseEntity.ok(userService.getSubscriberIds(userId))
    }
}
