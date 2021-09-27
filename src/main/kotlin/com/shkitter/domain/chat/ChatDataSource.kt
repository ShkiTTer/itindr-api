package com.shkitter.domain.chat

import com.shkitter.domain.chat.model.ChatWithLastMessage
import java.util.*

interface ChatDataSource {
    suspend fun getAllChatsForUser(userId: UUID): List<ChatWithLastMessage>
}