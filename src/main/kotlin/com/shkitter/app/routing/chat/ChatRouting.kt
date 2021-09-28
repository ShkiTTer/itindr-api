package com.shkitter.app.routing.chat

import com.shkitter.app.common.extensions.*
import com.shkitter.app.routing.chat.model.ChatResponse
import com.shkitter.app.routing.chat.model.ChatWithLastMessageResponse
import com.shkitter.app.routing.chat.model.CreateChatRequestDto
import com.shkitter.app.routing.chat.model.MessageResponse
import com.shkitter.domain.attachment.model.NewAttachment
import com.shkitter.domain.chat.ChatService
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.common.extensions.toUUID
import com.shkitter.domain.message.model.NewMessage
import com.shkitter.domain.validation.OffsetValidationRule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

private const val PART_MESSAGE = "messageText"

fun Routing.configureChat() {

    val chatService by inject<ChatService>()

    authenticate {
        get(ChatV1.getPath()) {
            val userId = call.principalUserIdOrThrow()
            val chats = chatService.getAllChatsForUser(userId)
            call.respondSuccess(chats.map {
                ChatWithLastMessageResponse.fromDomain(
                    data = it,
                    currentUserId = userId,
                    scheme = call.scheme
                )
            })
        }

        post(ChatV1.getPath()) {
            val userId = call.principalUserIdOrThrow()
            val request = call.receiveOrThrow<CreateChatRequestDto>().validateAndConvertToVerified()
            val newChat = chatService.createChat(firstUserId = userId, secondUserId = request.userId)
            call.respondSuccess(ChatResponse.fromDomain(data = newChat, currentUserId = userId, scheme = call.scheme))
        }

        post(ChatWithIdV1.Message.getPath()) {
            val userId = call.principalUserIdOrThrow()
            val chatId = call.getPathParameter<String>(ChatWithIdV1.paramName).toUUID()
                ?: throw BadRequestException("Invalid chat id")
            val multipart = call.receiveMultipart()

            val messageText = multipart.readAllParts()
                .filterIsInstance<PartData.FormItem>()
                .firstOrNull { it.name == PART_MESSAGE }
                ?.value ?: throw BadRequestException("Multipart must contains message item with name - $PART_MESSAGE")

            if (messageText.isBlank()) throw BadRequestException("Message text is required")

            val attachments = multipart.readAllParts()
                .filterIsInstance<PartData.FileItem>()
                .map { attachment ->
                    val fileName = attachment.originalFileName?.let { it.ifBlank { null } }
                        ?: throw BadRequestException("File must have original name")

                    NewAttachment(origFileName = fileName, bytes = attachment.streamProvider().readAllBytes())
                }

            val newMessage = chatService.createMessage(
                NewMessage(
                    chatId = chatId,
                    userId = userId,
                    text = messageText,
                    attachments = attachments
                )
            )

            call.respondSuccess(MessageResponse.fromDomain(data = newMessage, scheme = call.scheme))
        }

        get(ChatWithIdV1.Message.getPath()) {
            val chatId = call.getPathParameter<String>(ChatWithIdV1.paramName).toUUID()
                ?: throw BadRequestException("Invalid chat id")
            val limit = call.getQueryParameter<Int>(ChatWithIdV1.Message.limitParameterName)
                .also { OffsetValidationRule.validate(it).throwBadRequestIfError() }
            val offset = call.getQueryParameter<Int>(ChatWithIdV1.Message.offsetParameterName)
                .also { OffsetValidationRule.validate(it).throwBadRequestIfError() }

            val messages = chatService.getChatMessages(chatId = chatId, limit = limit, offset = offset)
            call.respondSuccess(messages.map { MessageResponse.fromDomain(data = it, scheme = call.scheme) })
        }
    }
}