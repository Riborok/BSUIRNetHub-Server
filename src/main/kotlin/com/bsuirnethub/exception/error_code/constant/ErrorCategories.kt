package com.bsuirnethub.exception.error_code.constant

internal object ErrorCategories {
    private const val MULTIPLIER = 1_00
    const val NOT_FOUND = 1 * MULTIPLIER
    const val NOT_FOUND_GROUP = 2 * MULTIPLIER
    const val ALREADY_EXISTS = 3 * MULTIPLIER
    const val MULTIPLE_FOUND = 4 * MULTIPLIER
    const val DUPLICATES_IN_REQUEST = 5 * MULTIPLIER
    const val INVALID_NEGATIVE_VALUE_IN_REQUEST = 6 * MULTIPLIER
    const val INVALID_PAGE_REQUEST = 7 * MULTIPLIER
    const val PARSING_ERROR = 8 * MULTIPLIER
    const val SERIALIZATION_ERROR = 9 * MULTIPLIER
    const val UNKNOWN_REQUEST = 10 * MULTIPLIER
    const val USER_NOT_CONNECTED = 11 * MULTIPLIER
    const val PARTICIPANT_ID_MISMATCH = 12 * MULTIPLIER
    const val SESSION_STATE_MISMATCH = 13 * MULTIPLIER
    const val INVALID_SESSION_STATE = 14 * MULTIPLIER
    const val OTHER = 99 * MULTIPLIER
}
