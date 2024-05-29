package com.bsuirnethub.exception.error_code

internal object ErrorCategories {
    private const val MULTIPLIER = 1_00
    const val NOT_FOUND = 1 * MULTIPLIER
    const val NOT_FOUND_GROUP = 2 * MULTIPLIER
    const val ALREADY_EXISTS = 3 * MULTIPLIER
    const val MULTIPLE_FOUND = 4 * MULTIPLIER
    const val DUPLICATES_IN_REQUEST = 5 * MULTIPLIER
    const val INVALID_NEGATIVE_VALUE = 6 * MULTIPLIER
    const val OTHER = 99 * MULTIPLIER
}
