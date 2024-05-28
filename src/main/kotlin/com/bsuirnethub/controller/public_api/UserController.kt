package com.bsuirnethub.controller.public_api

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.User
import com.bsuirnethub.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ApiPaths.PUBLIC)
class UserController(private val userService: UserService) {
    @GetMapping("/userInfo/{userId}")
    fun getUserInfo(
        @PathVariable userId: UserId
    ): ResponseEntity<User> {
        return ResponseEntity.ok(userService.getUserInfo(userId))
    }

    @GetMapping("/users")
    fun getUserIds(
    ): ResponseEntity<List<UserId?>> {
        return ResponseEntity.ok(userService.getUserIds())
    }
}
