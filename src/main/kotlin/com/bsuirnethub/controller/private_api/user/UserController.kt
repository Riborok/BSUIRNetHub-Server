package com.bsuirnethub.controller.private_api.user

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.service.UserService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping(ApiPaths.PRIVATE)
class UserController(private val userService: UserService) {
    @PutMapping("/last-seen")
    fun updateLastSeen(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestParam("lastSeen") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) lastSeen: LocalDateTime
    ): ResponseEntity<Any> {
        val userId = jwt.subject
        userService.updateLastSeen(userId, lastSeen)
        return ResponseEntity.ok().build()
    }
}
