package com.bsuirnethub.rtc.dialogue.webrtc

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.dialogue.Request

abstract class WebRTCRequest(
    override val requestType: WebRTCDialogueType,
    val recipientId: UserId
) : Request() {
    class STATE(
        recipientId: UserId
    ) : WebRTCRequest(WebRTCDialogueType.STATE, recipientId)

    class OFFER(
        recipientId: UserId,
        val content: String
    ) : WebRTCRequest(WebRTCDialogueType.OFFER, recipientId)

    class ANSWER(
        recipientId: UserId,
        val content: String
    ) : WebRTCRequest(WebRTCDialogueType.ANSWER, recipientId)

    class ICE(
        recipientId: UserId,
        val content: String
    ) : WebRTCRequest(WebRTCDialogueType.ICE, recipientId)
}