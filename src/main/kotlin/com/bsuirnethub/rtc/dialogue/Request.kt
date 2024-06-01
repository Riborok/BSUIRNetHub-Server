package com.bsuirnethub.rtc.dialogue

import com.bsuirnethub.rtc.dialogue.chat.ChatRequest
import com.bsuirnethub.rtc.dialogue.webrtc.WebRTCRequest
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "requestType"
)
@JsonSubTypes(
    JsonSubTypes.Type(ChatRequest.Mark::class, name = "MARK"),
    JsonSubTypes.Type(ChatRequest.Send::class, name = "SEND"),
    JsonSubTypes.Type(WebRTCRequest.STATE::class, name = "STATE"),
    JsonSubTypes.Type(WebRTCRequest.OFFER::class, name = "OFFER"),
    JsonSubTypes.Type(WebRTCRequest.ANSWER::class, name = "ANSWER"),
    JsonSubTypes.Type(WebRTCRequest.ICE::class, name = "ICE")
)
abstract class Request {
    abstract val requestType: DialogueType
}