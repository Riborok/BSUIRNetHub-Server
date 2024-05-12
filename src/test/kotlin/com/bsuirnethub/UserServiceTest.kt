package com.bsuirnethub

import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userService: UserService

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
        userService.createUser("user1")
        val user = userRepository.findByUserId("user1")
        assertNotNull(user)
    }

    @Test
    fun `test deleteUser`() {
        userService.createUser("user1")
        userService.deleteUser("user1")
        val user = userRepository.findByUserId("user1")
        assertNull(user)
    }

    @Test
    fun `test getUserById`() {
        userService.createUser("user1")
        val user = userService.getUserById("user1")
        assertEquals("user1", user.userId)
    }

    @Test
    fun `test addSubscription`() {
        userService.createUser("user1")
        userService.createUser("user2")
        userService.addSubscription("user1", "user2")
        val user = userService.getUserById("user1")
        assertNotNull(user.subscriptionsId)
        assertEquals(1, user.subscriptionsId!!.size)
    }

    @Test
    fun `test deleteSubscription`() {
        userService.createUser("user1")
        userService.createUser("user2")
        userService.addSubscription("user1", "user2")
        userService.deleteSubscription("user1", "user2")
        val user = userService.getUserById("user1")
        assertNotNull(user.subscriptionsId)
        assertEquals(0, user.subscriptionsId!!.size)
    }

    @Test
    fun `test getSubscriptions`() {
        userService.createUser("user1")
        userService.createUser("user2")
        userService.addSubscription("user1", "user2")
        val subscriptions = userService.getSubscriptions("user1")
        assertEquals(1, subscriptions.size)
        assertEquals("user2", subscriptions[0])
    }

    @Test
    fun `test getSubscribers`() {
        userService.createUser("user1")
        userService.createUser("user2")
        userService.addSubscription("user1", "user2")
        val subscribers = userService.getSubscribers("user2")
        assertNotNull(subscribers)
        assertEquals(1, subscribers.size)
        assertEquals("user1", subscribers[0])
    }

    @Test
    fun `test User Subscriptions And Subscribers After Deleting User`() {
        userService.createUser("user1")
        userService.createUser("user2")
        userService.addSubscription("user1", "user2")
        userService.addSubscription("user2", "user1")
        userService.deleteUser("user2")
        val subscribers = userService.getSubscribers("user1")
        val subscriptions = userService.getSubscriptions("user1")
        assertEquals(0, subscribers.size)
        assertEquals(0, subscriptions.size)
    }
}