package com.bsuirnethub.rtc.dialogue.chat

import com.bsuirnethub.model.Chat
import com.bsuirnethub.model.Message
import com.bsuirnethub.rtc.dialogue.Response

abstract class ChatResponse (
    override val responseType: ChatDialogueType
) : Response() {
    class Mark (
        val chat: Chat
    ): ChatResponse(ChatDialogueType.MARK)

    class Send (
        val chat: Chat,
        val message: Message
    ): ChatResponse(ChatDialogueType.SEND)
}
