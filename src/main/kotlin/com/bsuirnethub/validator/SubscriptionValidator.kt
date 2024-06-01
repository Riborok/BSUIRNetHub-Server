package com.bsuirnethub.validator

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.exception.error_code_exception.RestStatusException
import com.bsuirnethub.exception.error_code.rest.SubscriptionRestErrorCode
import org.springframework.stereotype.Component

@Component
class SubscriptionValidator {
    fun validateUserIsNotSubscription(userId: UserId, subscriptionId: UserId) {
        if (userId == subscriptionId) {
            throw RestStatusException(SubscriptionRestErrorCode.SELF_SUBSCRIPTION, subscriptionId)
        }
    }

    fun validateSubscriptionDeletion(deletedCount: Int, userId: UserId, subscriptionId: UserId) {
        if (deletedCount == 0)
            throw RestStatusException(SubscriptionRestErrorCode.SUBSCRIPTION_NOT_FOUND, subscriptionId)
    }

    fun validateSubscriptionDoesNotExists(userId: UserId, subscriptionId: UserId, operation: () -> UserId?): UserId? {
        return validateEntityDoesNotExists(operation, SubscriptionRestErrorCode.SUBSCRIPTION_ALREADY_EXISTS, subscriptionId)
    }
}