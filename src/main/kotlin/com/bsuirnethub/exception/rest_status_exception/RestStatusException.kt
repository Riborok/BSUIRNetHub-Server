package com.bsuirnethub.exception.rest_status_exception

import com.bsuirnethub.exception.error_code.rest.RestErrorCode

class RestStatusException(
    val restErrorCode: RestErrorCode,
    val source: Any?
) : RuntimeException(restErrorCode.message)
