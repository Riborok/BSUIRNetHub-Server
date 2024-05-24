package com.bsuirnethub.controller.public_api

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.UserId
import com.bsuirnethub.model.User
import com.bsuirnethub.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    ): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.getUserIds())
    }
}
