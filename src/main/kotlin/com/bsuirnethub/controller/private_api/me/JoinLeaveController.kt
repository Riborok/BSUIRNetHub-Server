package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me")
class JoinLeaveController(private val userService: UserService) {
    @PostMapping
    fun createUser(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<UserId?> {
        val myUserId = jwt.subject
        val createdUser = userService.createUser(myUserId)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @DeleteMapping
    fun deleteUser(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<Unit> {
        val myUserId = jwt.subject
        userService.deleteUser(myUserId)
        return ResponseEntity.noContent().build()
    }
}
