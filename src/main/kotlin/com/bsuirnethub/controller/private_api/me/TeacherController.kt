package com.bsuirnethub.controller.private_api.me

import com.bsuirnethub.ApiPaths
import com.bsuirnethub.alias.TeacherId
import com.bsuirnethub.service.TeacherService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${ApiPaths.PRIVATE}/me/teachers")
class TeacherController(private val teacherService: TeacherService) {
    @PostMapping("/{teacherId}")
    fun addTeacher(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable teacherId: TeacherId
    ): ResponseEntity<TeacherId?> {
        val myUserId = jwt.subject
        val addedTeacherId = teacherService.addTeacher(myUserId, teacherId)
        return ResponseEntity.status(HttpStatus.CREATED).body(addedTeacherId)
    }

    @DeleteMapping("/{teacherId}")
    fun deleteTeacher(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable teacherId: TeacherId
    ): ResponseEntity<Unit> {
        val myUserId = jwt.subject
        teacherService.deleteTeacher(myUserId, teacherId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun getTeacherIds(
        @AuthenticationPrincipal jwt: Jwt
    ): ResponseEntity<List<TeacherId?>> {
        val myUserId = jwt.subject
        val teacherIds = teacherService.getTeacherIds(myUserId)
        return ResponseEntity.ok(teacherIds)
    }
}