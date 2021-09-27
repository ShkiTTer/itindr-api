package com.shkitter.app.routing.chat.model

import com.shkitter.domain.chat.model.ChatWithLastMessage
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatWithLastMessageResponse(
    val id: String,
    val title: String,
    val avatar: String?,
    val lastMessage: MessageResponse? = null
) {

    companion object {
        fun fromDomain(data: ChatWithLastMessage, currentUserId: UUID, scheme: String) = ChatWithLastMessageResponse(
            id = data.chat.id.toString(),
            title = if (data.chat.firstUser.id != currentUserId) {
                data.chat.firstUser.name
            } else {
                data.chat.secondUser.name
            },
            avatar = if (data.chat.firstUser.id != currentUserId) {
                data.chat.firstUser.avatar
            } else {
                data.chat.secondUser.avatar
            },
            lastMessage = data.lastMessage?.let { MessageResponse.fromDomain(data = it, scheme = scheme) }
        )
    }
}
