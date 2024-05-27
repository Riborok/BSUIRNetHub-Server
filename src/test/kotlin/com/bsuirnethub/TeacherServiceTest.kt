package com.bsuirnethub

import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.repository.TeacherRepository
import com.bsuirnethub.repository.UserRepository
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
    @Autowired val userRepository: UserRepository,
    @Autowired private val teacherRepository: TeacherRepository,
    @Autowired val userService: UserService,
    @Autowired private val teacherService: TeacherService
) {
    @BeforeEach
    fun setUp() {
        teacherRepository.deleteAll()
        userRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        teacherRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun `test addTeacher`() {
        val userId = "user"
        val teacherId = "teacher"
        userService.createUser(userId)
        teacherService.addTeacher(userId, teacherId)
        val teacherIds = teacherService.getTeacherIds(userId)
        assertEquals(1, teacherIds.size)
        assertEquals(teacherId, teacherIds[0])
    }

    @Test
    fun `test addTeacher with existing teacher`() {
        val userId = "user"
        val teacherId = "teacher"
        userService.createUser(userId)
        teacherService.addTeacher(userId, teacherId)
        assertThrows<RestStatusException> {
            teacherService.addTeacher(userId, teacherId)
        }
    }

    @Test
    fun `test deleteTeacher`() {
        val userId = "user"
        val teacherId = "teacher"
        userService.createUser(userId)
        teacherService.addTeacher(userId, teacherId)
        teacherService.deleteTeacher(userId, teacherId)
        val teacherIds = teacherService.getTeacherIds(userId)
        assertFalse(teacherIds.contains(teacherId))
    }

    @Test
    fun `test deleteTeacher with non-existent teacher`() {
        val userId = "user"
        val teacherId = "teacher"
        userService.createUser(userId)
        assertThrows<RestStatusException> {
            teacherService.deleteTeacher(userId, teacherId)
        }
    }

    @Test
    fun `test getTeacherIds`() {
        val userId = "user"
        userService.createUser(userId)
        val teacherIdsToAdd = List(42) { "teacher$it" }
        teacherIdsToAdd.forEach { teacherService.addTeacher(userId, it) }
        val teacherIds = teacherService.getTeacherIds(userId)
        assertEquals(42, teacherIds.size)
        assertTrue(teacherIds.containsAll(teacherIdsToAdd))
    }
}
