package com.bsuirnethub.controller.private_api.user

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.model.Subscriber
import com.bsuirnethub.service.SubscriberService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/subscribers")
class SubscriberController(private val subscriberService: SubscriberService) {
    @GetMapping
    fun getAllSubscribers(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<List<Subscriber>> {
        val userId = jwt.subject
        val subscribers = subscriberService.getAllSubscribers(userId)
        return ResponseEntity.ok(subscribers)
    }

    @PutMapping("/{subscriberId}")
    fun addSubscriber(@AuthenticationPrincipal jwt: Jwt, @PathVariable subscriberId: String): ResponseEntity<Any> {
        val userId = jwt.subject
        subscriberService.addSubscriber(userId, subscriberId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{subscriberId}")
    fun deleteSubscriber(@AuthenticationPrincipal jwt: Jwt, @PathVariable subscriberId: String): ResponseEntity<Any> {
        val userId = jwt.subject
        subscriberService.deleteSubscriber(userId, subscriberId)
        return ResponseEntity.ok().build()
    }
}
