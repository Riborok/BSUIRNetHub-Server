package com.bsuirnethub.exception.error_code

import com.bsuirnethub.exception.error_code.constant.ErrorCategories
import com.bsuirnethub.exception.error_code.constant.ModelCodes

enum class WebRTCErrorCode(errorCategory: Int, override val message: String) : ErrorCode {
    USER_NOT_CONNECTED(ErrorCategories.USER_NOT_CONNECTED, "User is not connected"),
    PARTICIPANT_ID_MISMATCH(ErrorCategories.PARTICIPANT_ID_MISMATCH, "Participant ID mismatch"),
    SESSION_STATE_MISMATCH(ErrorCategories.SESSION_STATE_MISMATCH, "Session state mismatch"),
    INVALID_SESSION_STATE(ErrorCategories.INVALID_SESSION_STATE, "Invalid session state");

    override val code: Int = ModelCodes.WEB_RTC + errorCategory
}