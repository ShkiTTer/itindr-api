package com.shkitter.domain.chat

import com.shkitter.domain.chat.model.Chat
import com.shkitter.domain.chat.model.ChatWithLastMessage
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.message.MessageDataSource
import com.shkitter.domain.user.UserDataSource
import java.util.*

class ChatServiceImpl(
    private val chatDataSource: ChatDataSource,
    private val userDataSource: UserDataSource,
    private val messageDataSource: MessageDataSource
) : ChatService {

    override suspend fun getAllChatsForUser(userId: UUID): List<ChatWithLastMessage> {
        checkUserExistOrThrow(userId)
        return chatDataSource.getAllChatsForUser(userId).map { chat ->
            ChatWithLastMessage(
                chat = chat,
                lastMessage = messageDataSource.getLastMessageForChat(chatId = chat.id)
            )
        }
    }

    override suspend fun createChat(firstUserId: UUID, secondUserId: UUID): Chat {
        checkUserExistOrThrow(firstUserId)
        checkUserExistOrThrow(secondUserId)
        return chatDataSource.createChat(firstUserId, secondUserId)
    }

    private suspend fun checkUserExistOrThrow(userId: UUID) {
        userDataSource.getUserById(userId) ?: throw NotFoundException("User not found")
    }
}