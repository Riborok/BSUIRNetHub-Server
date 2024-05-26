package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.RestStatusException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class SubscriptionValidator {
    fun validateUserIsNotSubscription(userId: UserId, subscriptionId: UserId) {
        if (userId == subscriptionId) {
            throw RestStatusException("You can't subscribe to yourself", HttpStatus.BAD_REQUEST)
        }
    }

    fun validateSubscriptionDeletion(deletedCount: Int, userId: UserId, subscriptionId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException("Subscription with id $subscriptionId not found for user with id $userId", HttpStatus.NOT_FOUND)
    }

    fun validateSubscriptionDoesNotExists(userId: UserId, subscriptionId: UserId, operation: () -> UserId?): UserId? {
        return try {
            operation()
        } catch (e: DataIntegrityViolationException) {
            throw RestStatusException("Subscription already exists for userId $userId and subscriptionId $subscriptionId", HttpStatus.CONFLICT)
        }
    }
}