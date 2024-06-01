package com.bsuirnethub.rtc.dialogue

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object DialogueParser {
    private val objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    fun parseRequest(payload: String): Request {
        return try {
            objectMapper.readValue(payload, Request::class.java)
        } catch (_: JsonProcessingException) {
            throw Exception()
        }
    }

    fun serialize(response: Any): String {
        return try {
            objectMapper.writeValueAsString(response)
        } catch (_: JsonProcessingException) {
            throw Exception()
        }
    }
}
