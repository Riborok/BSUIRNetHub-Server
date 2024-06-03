package com.bsuirnethub.rtc.dialogue.webrtc

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.dialogue.Request
import com.bsuirnethub.rtc.session.SessionState

abstract class WebRTCRequest(
    override val requestType: WebRTCDialogueType,
    val recipientId: UserId
) : Request() {
    class STATE(
        recipientId: UserId,
        val sessionState: SessionState
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