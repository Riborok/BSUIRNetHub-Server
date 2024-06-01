package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.exception.rest_status_exception.RestStatusException
import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userService: UserService,
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

    private val userId1 = "user1"
    private val userId2 = "user2"

    @Test
    fun `test createUser`() {
        userService.createUser(userId1)
        val user = userRepository.findByUserId(userId1)
        assertNotNull(user)
    }

    @Test
    fun `test createUser With Duplicate UserId`() {
        userService.createUser(userId1)
        assertThrows<RestStatusException> {
            userService.createUser(userId1)
        }
    }

    @Test
    fun `test deleteUser`() {
        userService.createUser(userId1)
        userService.deleteUser(userId1)
        val user = userRepository.findByUserId(userId1)
        assertNull(user)
    }

    @Test
    fun `test deleteUser With Non-Existent User`() {
        assertThrows<RestStatusException> {
            userService.deleteUser(userId1)
        }
    }

    @Test
    fun `test getUserInfo`() {
        userService.createUser(userId1)
        val user = userService.getUserInfo(userId1)
        assertEquals(userId1, user.userId)
    }

    @Test
    fun `test getUserIds`() {
        userService.createUser(userId1)
        userService.createUser(userId2)
        val userIds = userService.getUserIds()
        assertEquals(2, userIds.size)
        assertTrue(userIds.any { it == userId1 })
        assertTrue(userIds.any { it == userId2 })
    }
}
