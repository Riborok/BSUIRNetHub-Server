package com.bsuirnethub.exception.error_code

import com.bsuirnethub.exception.error_code.constant.ErrorCategories
import com.bsuirnethub.exception.error_code.constant.ModelCodes

enum class JsonErrorCode(errorCategory: Int, override val message: String): ErrorCode {
    JSON_PARSING_ERROR(ErrorCategories.PARSING_ERROR, "Error parsing JSON"),
    JSON_SERIALIZATION_ERROR(ErrorCategories.SERIALIZATION_ERROR, "Error serializing JSON");

    override val code: Int = ModelCodes.JSON + errorCategory
}