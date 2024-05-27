package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.GroupId
import com.bsuirnethub.service.GroupService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me/groups")
class GroupController(private val groupService: GroupService) {
    @PostMapping("/{groupId}")
    fun addGroup(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable groupId: GroupId
    ): ResponseEntity<GroupId?> {
        val myUserId = jwt.subject
        val addedGroupId = groupService.addGroup(myUserId, groupId)
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGroupId)
    }

    @DeleteMapping("/{groupId}")
    fun deleteGroup(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable groupId: GroupId
    ): ResponseEntity<Unit> {
        val myUserId = jwt.subject
        groupService.deleteGroup(myUserId, groupId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getGroupIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<GroupId?>> {
        val myUserId = jwt.subject
        val groupIds = groupService.getGroupIds(myUserId)
        return ResponseEntity.ok(groupIds)
    }
}