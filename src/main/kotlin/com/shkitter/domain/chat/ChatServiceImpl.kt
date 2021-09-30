package com.shkitter.domain.chat

import com.shkitter.domain.attachment.AttachmentDataSource
import com.shkitter.domain.chat.model.Chat
import com.shkitter.domain.chat.model.ChatWithLastMessage
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.common.extensions.getFileExtensions
import com.shkitter.domain.common.utils.DateTimeUtils
import com.shkitter.domain.files.FilesDataSource
import com.shkitter.domain.message.MessageDataSource
import com.shkitter.domain.message.model.Message
import com.shkitter.domain.message.model.NewMessage
import com.shkitter.domain.user.UserDataSource
import java.util.*

class ChatServiceImpl(
    private val chatDataSource: ChatDataSource,
    private val userDataSource: UserDataSource,
    private val messageDataSource: MessageDataSource,
    private val filesDataSource: FilesDataSource,
    private val attachmentDataSource: AttachmentDataSource
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
        checkExistMutualLike(firstUserId, secondUserId)

        val existChat = chatDataSource.getChatBetweenUsers(firstUserId, secondUserId)
        return existChat ?: chatDataSource.createChat(firstUserId, secondUserId)
    }

    override suspend fun createMessage(newMessage: NewMessage): Message {
        checkUserExistOrThrow(newMessage.userId)
        checkChatExistOrThrow(newMessage.chatId)

        val message = messageDataSource.createMessage(
            userId = newMessage.userId,
            chatId = newMessage.chatId,
            text = newMessage.text
        )

        val attachments = newMessage.attachments.map { file ->
            val filePath = filesDataSource.saveFile(
                fileName = createAttachmentFileName(),
                extension = file.origFileName.getFileExtensions(),
                bytes = file.bytes,
                deleteOnExit = true
            )
            attachmentDataSource.createAttachment(messageId = message.id, filePath = filePath)
        }

        return message.copy(attachments = attachments)
    }

    override suspend fun getChatMessages(chatId: UUID, limit: Int, offset: Int): List<Message> {
        checkChatExistOrThrow(chatId)
        return chatDataSource.getChatMessages(chatId = chatId, limit = limit, offset = offset)
    }

    private suspend fun checkUserExistOrThrow(userId: UUID) {
        userDataSource.getUserById(userId) ?: throw NotFoundException("User not found")
    }

    private suspend fun checkChatExistOrThrow(chatId: UUID) {
        chatDataSource.getChatById(chatId) ?: throw NotFoundException("Chat not found")
    }

    private suspend fun checkExistMutualLike(firstUserId: UUID, secondUserId: UUID) {
        if (!userDataSource.hasMutualLike(firstUserId, secondUserId)) {
            throw NotFoundException("Users have not mutual like")
        }
    }

    private fun createAttachmentFileName() = "${UUID.randomUUID()}_${DateTimeUtils.getCurrentSeconds()}"
}