package com.bsuirnethub.exception.error_code

import com.bsuirnethub.exception.error_code.constant.ErrorCategories
import com.bsuirnethub.exception.error_code.constant.ModelCodes

enum class OtherErrorCode(errorCategory: Int, override val message: String) : ErrorCode {
    UNKNOWN_REQUEST(ErrorCategories.UNKNOWN_REQUEST, "Unknown request format");

    override val code: Int = ModelCodes.OTHER + errorCategory
}
