package com.bsuirnethub.exception.error_code

import org.springframework.http.HttpStatus

enum class SubscriptionErrorCode(errorCategory: Int, override val message: String, override val status: HttpStatus) :
    ErrorCode {
    SUBSCRIPTION_NOT_FOUND(ErrorCategories.NOT_FOUND, "Subscription not found", HttpStatus.NOT_FOUND),
    SUBSCRIPTION_ALREADY_EXISTS(ErrorCategories.ALREADY_EXISTS, "Subscription already exists", HttpStatus.CONFLICT),
    SELF_SUBSCRIPTION(ErrorCategories.OTHER, "You can't subscribe to yourself", HttpStatus.BAD_REQUEST);

    override val code: Int = ModelCodes.SUBSCRIPTION + errorCategory
}