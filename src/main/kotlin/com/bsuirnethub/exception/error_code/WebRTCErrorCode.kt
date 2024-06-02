package com.bsuirnethub.exception.error_code

import com.bsuirnethub.exception.error_code.constant.ErrorCategories
import com.bsuirnethub.exception.error_code.constant.ModelCodes

enum class WebRTCErrorCode(errorCategory: Int, override val message: String) : ErrorCode {
    USER_NOT_CONNECTED(ErrorCategories.USER_NOT_CONNECTED, "User is not connected");

    override val code: Int = ModelCodes.WEB_RTC + errorCategory
}