package com.bsuirnethub

import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.SubscriptionService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SubscriptionServiceTest (
    @Autowired private val userRepository: UserRepository,
    @Autowired private val userService: UserService,
    @Autowired private val subscriptionService: SubscriptionService
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
    fun `test addSubscription`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        subscriptionService.addSubscription(userId1, userId2)
        val user1 = userService.getUserInfo(userId1)
        val user2 = userService.getUserInfo(userId2)
        assertEquals(1, user1.subscriptionIds!!.size)
        assertEquals(1, user2.subscriberIds!!.size)
    }

    @Test
    fun `test addSubscription multiple times to the same user`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        for (i in 1..42)
            subscriptionService.addSubscription(userId1, userId2)
        val user1 = userService.getUserInfo(userId1)
        val user2 = userService.getUserInfo(userId2)
        assertEquals(1, user1.subscriptionIds!!.size)
        assertEquals(1, user2.subscriberIds!!.size)
    }

    @Test
    fun `test deleteSubscription`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        subscriptionService.addSubscription(userId1, userId2)
        subscriptionService.deleteSubscription(userId1, userId2)
        val user1 = userService.getUserInfo(userId1)
        val user2 = userService.getUserInfo(userId2)
        assertEquals(0, user1.subscriptionIds!!.size)
        assertEquals(0, user2.subscriberIds!!.size)
    }

    @Test
    fun `test getSubscriptions`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        subscriptionService.addSubscription(userId1, userId2)
        val subscriptions = subscriptionService.getSubscriptionIds(userId1)
        assertEquals(1, subscriptions.size)
        assertEquals(userId2, subscriptions[0].userId)
    }

    @Test
    fun `test getSubscribers`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        subscriptionService.addSubscription(userId1, userId2)
        val subscribers = subscriptionService.getSubscriberIds(userId2)
        assertEquals(1, subscribers.size)
        assertEquals(userId1, subscribers[0].userId)
    }

    @Test
    fun `test Subscriptions And Subscribers After Deleting User`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        subscriptionService.addSubscription(userId1, userId2)
        subscriptionService.addSubscription(userId2, userId1)
        userService.deleteUser(userId2)
        val subscribers = subscriptionService.getSubscriberIds(userId1)
        val subscriptions = subscriptionService.getSubscriptionIds(userId1)
        assertEquals(0, subscribers.size)
        assertEquals(0, subscriptions.size)
    }
}