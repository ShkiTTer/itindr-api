package com.shkitter.domain.chat

import com.shkitter.domain.chat.model.Chat
import java.util.*

interface ChatDataSource {
    suspend fun getAllChatsForUser(userId: UUID): List<Chat>
    suspend fun createChat(firstUserId: UUID, secondUserId: UUID): Chat
}