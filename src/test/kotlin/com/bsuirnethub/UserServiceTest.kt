package com.bsuirnethub

import com.bsuirnethub.exception.RestStatusException
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
    @Autowired private val userService: UserService
) {
    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
    }

    @Test
    fun `test createUser`() {
        val userId = "user"
        userService.createUser(userId)
        val user = userRepository.findByUserId(userId)
        assertNotNull(user)
    }

    @Test
    fun `test createUser With Duplicate UserId`() {
        val userId = "user"
        userService.createUser(userId)
        assertThrows<RestStatusException> {
            userService.createUser(userId)
        }
    }

    @Test
    fun `test deleteUser`() {
        val userId = "user"
        userService.createUser(userId)
        userService.deleteUser(userId)
        val user = userRepository.findByUserId(userId)
        assertNull(user)
    }

    @Test
    fun `test deleteUser with non-existent user`() {
        val userId = "user"
        assertThrows<RestStatusException> {
            userService.deleteUser(userId)
        }
    }

    @Test
    fun `test getUserInfo`() {
        val userId = "user"
        userService.createUser(userId)
        val user = userService.getUserInfo(userId)
        assertEquals(userId, user.userId)
    }

    @Test
    fun `test getUserIds`() {
        userService.createUser("user1")
        userService.createUser("user2")
        val userIds = userService.getUserIds()
        assertEquals(2, userIds.size)
        assertTrue(userIds.any { it == "user1" })
        assertTrue(userIds.any { it == "user2" })
    }
}
