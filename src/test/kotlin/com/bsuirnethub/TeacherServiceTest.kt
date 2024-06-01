package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.service.TeacherService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TeacherServiceTest(
    @Autowired private val teacherService: TeacherService,
    @Autowired private val userService: UserService,
    @Autowired val userInitializer: UserInitializer,
    @Autowired private val databaseCleanup: DatabaseCleanup
) {
    @BeforeEach
    fun setUp() {
        databaseCleanup.clearDatabase()
    }

    @AfterEach
    fun tearDown() {
        databaseCleanup.clearDatabase()
    }

    private val teacherId = "teacher"

    @Test
    fun `test addTeacher`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        teacherService.addTeacher(userId, teacherId)
        val teacherIds = teacherService.getTeacherIds(userId)
        assertEquals(1, teacherIds.size)
        assertEquals(teacherId, teacherIds[0])
    }

    @Test
    fun `test addTeacher With Existing Teacher`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        teacherService.addTeacher(userId, teacherId)
        assertThrows<RestStatusException> {
            teacherService.addTeacher(userId, teacherId)
        }
    }

    @Test
    fun `test deleteTeacher`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        teacherService.addTeacher(userId, teacherId)
        teacherService.deleteTeacher(userId, teacherId)
        val teacherIds = teacherService.getTeacherIds(userId)
        assertFalse(teacherIds.contains(teacherId))
    }

    @Test
    fun `test deleteTeacher With Non-Existent Teacher`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        assertThrows<RestStatusException> {
            teacherService.deleteTeacher(userId, teacherId)
        }
    }

    @Test
    fun `test getTeacherIds`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        val teacherIdsToAdd = List(42) { "$teacherId$it" }
        teacherIdsToAdd.forEach { teacherService.addTeacher(userId, it) }
        val teacherIds = teacherService.getTeacherIds(userId)
        assertEquals(42, teacherIds.size)
        assertTrue(teacherIds.containsAll(teacherIdsToAdd))
    }

    @Test
    fun `test Teacher After Deleting User`() {
        val userIds = userInitializer.createAndSaveUsers(1).userIds
        val userId = userIds[0]
        val teacherIdsToAdd = List(42) { "$teacherId$it" }
        teacherIdsToAdd.forEach { teacherService.addTeacher(userId, it) }
        userService.deleteUser(userId)
        assertEquals(0, databaseCleanup.teacherRepository.count())
    }
}
