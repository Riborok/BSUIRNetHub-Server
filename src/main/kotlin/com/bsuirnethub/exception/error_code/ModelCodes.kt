package com.bsuirnethub.exception.error_code

internal object ModelCodes {
    private const val MULTIPLIER = 1_00_00_00
    const val USER = 1 * MULTIPLIER
    const val GROUP = 2 * MULTIPLIER
    const val TEACHER = 3 * MULTIPLIER
    const val SUBSCRIPTION = 4 * MULTIPLIER
    const val CHAT = 5 * MULTIPLIER
    const val USER_CHAT = 6 * MULTIPLIER
}
