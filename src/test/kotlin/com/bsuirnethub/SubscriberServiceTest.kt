package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
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
    @Autowired private val subscriptionService: SubscriptionService,
    @Autowired private val subscriberService: SubscriberService,
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

    @Test
    fun `test getSubscribers`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        val subscribers = subscriberService.getSubscriberIds(userIds[1])
        assertEquals(1, subscribers.size)
        assertEquals(userIds[0], subscribers[0])
    }

    @Test
    fun `test Subscriptions And Subscribers After Deleting User`() {
        val userIds = userInitializer.createAndSaveUsers(3).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        subscriptionService.addSubscription(userIds[0], userIds[2])
        subscriptionService.addSubscription(userIds[2], userIds[0])
        userService.deleteUser(userIds[1])
        val subscribers = subscriberService.getSubscriberIds(userIds[0])
        val subscriptions = subscriptionService.getSubscriptionIds(userIds[0])
        assertEquals(1, subscribers.size)
        assertEquals(userIds[2], subscribers[0])
        assertEquals(1, subscriptions.size)
        assertEquals(userIds[2], subscriptions[0])
    }
}