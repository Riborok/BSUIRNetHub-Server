package com.bsuirnethub.rtc.dialogue

import com.bsuirnethub.rtc.dialogue.chat.ChatResponse
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "responseType"
)
@JsonSubTypes(
    JsonSubTypes.Type(ChatResponse.Mark::class, name = "MARK"),
    JsonSubTypes.Type(ChatResponse.Send::class, name = "SEND"),
/*    JsonSubTypes.Type(WebRTCResponse.STATE::class, name = "STATE"),
    JsonSubTypes.Type(WebRTCResponse.OFFER::class, name = "OFFER"),
    JsonSubTypes.Type(WebRTCResponse.ANSWER::class, name = "ANSWER"),
    JsonSubTypes.Type(WebRTCResponse.ICE::class, name = "ICE")*/
)
abstract class Response {
    abstract val responseType: DialogueType
}