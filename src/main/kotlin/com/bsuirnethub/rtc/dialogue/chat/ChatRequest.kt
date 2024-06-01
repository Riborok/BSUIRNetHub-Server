package com.bsuirnethub.rtc.dialogue.chat

import com.bsuirnethub.rtc.dialogue.Request

abstract class ChatRequest(
    override val requestType: ChatDialogueType
) : Request() {
    class Mark (
        val chatId: Long,
        val readMessage: Int
    ): ChatRequest(ChatDialogueType.MARK)

    class Send (
        val chatId: Long,
        val content: String
    ): ChatRequest(ChatDialogueType.SEND)
}