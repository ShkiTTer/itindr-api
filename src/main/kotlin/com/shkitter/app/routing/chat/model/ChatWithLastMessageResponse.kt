package com.shkitter.app.routing.chat.model

import com.shkitter.domain.chat.model.ChatWithLastMessage
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatWithLastMessageResponse(
    val chat: ChatResponse,
    val lastMessage: MessageResponse? = null
) {

    companion object {
        fun fromDomain(data: ChatWithLastMessage, currentUserId: UUID, scheme: String) = ChatWithLastMessageResponse(
            chat = ChatResponse.fromDomain(data = data.chat, currentUserId = currentUserId, scheme = scheme),
            lastMessage = data.lastMessage?.let { MessageResponse.fromDomain(data = it, scheme = scheme) }
        )
    }
}
