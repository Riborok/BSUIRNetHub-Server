package com.bsuirnethub.exception.error_code_exception

import com.bsuirnethub.exception.error_code.rest.RestErrorCode

class RestStatusException(
    override val errorCode: RestErrorCode,
    source: Any?
) : ErrorCodeException(errorCode, source)
