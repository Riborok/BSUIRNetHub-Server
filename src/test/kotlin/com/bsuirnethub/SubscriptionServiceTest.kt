package com.bsuirnethub

import com.bsuirnethub.component.DatabaseCleanup
import com.bsuirnethub.component.UserInitializer
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.service.SubscriberService
import com.bsuirnethub.service.SubscriptionService
import com.bsuirnethub.service.UserService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SubscriptionServiceTest (
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
    fun `test addSubscription`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        val user1SubscriptionIds = subscriptionService.getSubscriptionIds(userIds[0])
        val user2SubscriberIds = subscriberService.getSubscriberIds(userIds[1])
        assertEquals(1, user1SubscriptionIds.size)
        assertEquals(1, user2SubscriberIds.size)
    }

    @Test
    fun `test addSubscription Multiple Times To The Same User`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        assertThrows<RestStatusException> {
            subscriptionService.addSubscription(userIds[0], userIds[1])
        }
    }

    @Test
    fun `test deleteSubscription`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        subscriptionService.deleteSubscription(userIds[0], userIds[1])
        val user1SubscriptionIds = subscriptionService.getSubscriptionIds(userIds[0])
        val user2SubscriberIds = subscriberService.getSubscriberIds(userIds[1])
        assertEquals(0, user1SubscriptionIds.size)
        assertEquals(0, user2SubscriberIds.size)
    }

    @Test
    fun `test deleteSubscription With Non-Existent Subscription`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        assertThrows<RestStatusException> {
            subscriptionService.deleteSubscription(userIds[0], userIds[1])
        }
    }

    @Test
    fun `test getSubscriptions`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        val subscriptions = subscriptionService.getSubscriptionIds(userIds[0])
        assertEquals(1, subscriptions.size)
        assertEquals(userIds[1], subscriptions[0])
    }

    @Test
    fun `test Subscriptions And Subscribers After Deleting User`() {
        val userIds = userInitializer.createAndSaveUsers(2).userIds
        subscriptionService.addSubscription(userIds[0], userIds[1])
        subscriptionService.addSubscription(userIds[1], userIds[0])
        userService.deleteUser(userIds[1])
        val subscribers = subscriberService.getSubscriberIds(userIds[0])
        val subscriptions = subscriptionService.getSubscriptionIds(userIds[0])
        assertEquals(0, subscribers.size)
        assertEquals(0, subscriptions.size)
        assertEquals(0, databaseCleanup.subscriptionRepository.count())
    }
}