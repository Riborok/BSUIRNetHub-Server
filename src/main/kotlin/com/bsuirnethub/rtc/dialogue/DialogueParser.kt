package com.bsuirnethub.rtc.dialogue

import com.bsuirnethub.exception.error_code.JsonErrorCode
import com.bsuirnethub.exception.error_code_exception.ErrorCodeException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object DialogueParser {
    private val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    fun parseRequest(payload: String): Request {
        return try {
            objectMapper.readValue(payload, Request::class.java)
        } catch (_: JsonProcessingException) {
            throw ErrorCodeException(JsonErrorCode.JSON_PARSING_ERROR, payload)
        }
    }

    fun serialize(any: Any): String {
        return try {
            objectMapper.writeValueAsString(any)
        } catch (_: JsonProcessingException) {
            throw ErrorCodeException(JsonErrorCode.JSON_SERIALIZATION_ERROR, any)
        }
    }
}
