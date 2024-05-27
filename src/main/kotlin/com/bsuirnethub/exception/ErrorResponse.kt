package com.bsuirnethub.exception

class ErrorResponse (
    val code: Int,
    val message: String,
    val source: Any,
)
