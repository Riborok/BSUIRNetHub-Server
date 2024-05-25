package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.model.User
import com.bsuirnethub.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me")
class UserJoinLeaveController(private val userService: UserService) {
    @PostMapping("/join")
    fun createUser(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<User> {
        val userId = jwt.subject
        val createdUser = userService.createUser(userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @DeleteMapping("/quit")
    fun deleteUser(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<Any> {
        val userId = jwt.subject
        userService.deleteUser(userId)
        return ResponseEntity.noContent().build()
    }
}
