package com.bsuirnethub.component.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.RestStatusException
import com.bsuirnethub.exception.error_code.SubscriptionErrorCode
import org.springframework.stereotype.Component

@Component
class SubscriptionValidator {
    fun validateUserIsNotSubscription(userId: UserId, subscriptionId: UserId) {
        if (userId == subscriptionId) {
            throw RestStatusException(SubscriptionErrorCode.SELF_SUBSCRIPTION, subscriptionId)
        }
    }

    fun validateSubscriptionDeletion(deletedCount: Int, userId: UserId, subscriptionId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException(SubscriptionErrorCode.SUBSCRIPTION_NOT_FOUND, subscriptionId)
    }

    fun validateSubscriptionDoesNotExists(userId: UserId, subscriptionId: UserId, operation: () -> UserId?): UserId? {
        return validateEntityDoesNotExists(operation, SubscriptionErrorCode.SUBSCRIPTION_ALREADY_EXISTS, subscriptionId)
    }
}