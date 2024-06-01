package com.bsuirnethub.rtc.dialogue.webrtc

import com.bsuirnethub.rtc.dialogue.DialogueType

enum class WebRTCDialogueType: DialogueType {
    STATE,
    OFFER,
    ANSWER,
    ICE
}