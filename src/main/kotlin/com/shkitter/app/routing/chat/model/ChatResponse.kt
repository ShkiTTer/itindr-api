package com.shkitter.app.routing.chat.model

import com.shkitter.domain.chat.model.Chat
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ChatResponse(
    val id: String,
    val title: String,
    val avatar: String?
) {

    companion object {
        fun fromDomain(data: Chat, currentUserId: UUID, scheme: String) = ChatResponse(
            id = data.id.toString(),
            title = if (data.firstUser.id != currentUserId) {
                data.firstUser.name
            } else {
                data.secondUser.name
            },
            avatar = if (data.firstUser.id != currentUserId) {
                data.firstUser.avatar
            } else {
                data.secondUser.avatar
            }
        )
    }
}
