package com.bsuirnethub

import com.bsuirnethub.repository.SubscriptionRepository
import com.bsuirnethub.repository.UserRepository
import com.bsuirnethub.service.SubscriberService
import com.bsuirnethub.service.SubscriptionService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SubscriberServiceTest(
    @Autowired private val userRepository: UserRepository,
    @Autowired private val subscriptionRepository: SubscriptionRepository,
    @Autowired private val userService: UserService,
    @Autowired private val subscriptionService: SubscriptionService,
    @Autowired private val subscriberService: SubscriberService
) {
    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        subscriptionRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        userRepository.deleteAll()
        subscriptionRepository.deleteAll()
    }

    @Test
    fun `test getSubscribers`() {
        val userId1 = "user1"
        val userId2 = "user2"
        userService.createUser(userId1)
        userService.createUser(userId2)
        subscriptionService.addSubscription(userId1, userId2)
        val subscribers = subscriberService.getSubscriberIds(userId2)
        assertEquals(1, subscribers.size)
        assertEquals(userId1, subscribers[0])
    }

    @Test
    fun `test Subscriptions And Subscribers After Deleting User`() {
        val userId1 = "user1"
        val userId2 = "user2"
        val userId3 = "user3"
        userService.createUser(userId1)
        userService.createUser(userId2)
        userService.createUser(userId3)
        subscriptionService.addSubscription(userId1, userId2)
        subscriptionService.addSubscription(userId1, userId3)
        subscriptionService.addSubscription(userId3, userId1)
        userService.deleteUser(userId2)
        val subscribers = subscriberService.getSubscriberIds(userId1)
        val subscriptions = subscriptionService.getSubscriptionIds(userId1)
        assertEquals(1, subscribers.size)
        assertEquals(userId3, subscribers[0])
        assertEquals(1, subscriptions.size)
        assertEquals(userId3, subscriptions[0])
    }
}