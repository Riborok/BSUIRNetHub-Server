package com.bsuirnethub.rtc.dialogue.webrtc

import com.bsuirnethub.alias.UserId
import com.bsuirnethub.rtc.dialogue.Response
import com.bsuirnethub.rtc.session.SessionState

abstract class WebRTCResponse (
    override val responseType: WebRTCDialogueType,
    val senderId: UserId
) : Response() {
    class STATE(
        senderId: UserId,
        val sessionState: SessionState
    ) : WebRTCResponse(WebRTCDialogueType.STATE, senderId)

    class OFFER(
        senderId: UserId,
        val content: String
    ) : WebRTCResponse(WebRTCDialogueType.OFFER, senderId)

    class ANSWER(
        senderId: UserId,
        val content: String
    ) : WebRTCResponse(WebRTCDialogueType.ANSWER, senderId)

    class ICE(
        senderId: UserId,
        val content: String
    ) : WebRTCResponse(WebRTCDialogueType.ICE, senderId)
}