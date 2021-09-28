package com.shkitter.domain.chat

import com.shkitter.domain.chat.model.Chat
import com.shkitter.domain.message.model.Message
import java.util.*

interface ChatDataSource {
    suspend fun getAllChatsForUser(userId: UUID): List<Chat>
    suspend fun createChat(firstUserId: UUID, secondUserId: UUID): Chat
    suspend fun getChatById(chatId: UUID): Chat?
    suspend fun getChatMessages(chatId: UUID, limit: Int, offset: Int): List<Message>
}