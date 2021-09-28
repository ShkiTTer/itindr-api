package com.shkitter.domain.chat

import com.shkitter.domain.chat.model.Chat
import com.shkitter.domain.chat.model.ChatWithLastMessage
import com.shkitter.domain.message.model.Message
import com.shkitter.domain.message.model.NewMessage
import java.util.*

interface ChatService {
    suspend fun getAllChatsForUser(userId: UUID): List<ChatWithLastMessage>
    suspend fun createChat(firstUserId: UUID, secondUserId: UUID): Chat

    suspend fun createMessage(newMessage: NewMessage): Message
}