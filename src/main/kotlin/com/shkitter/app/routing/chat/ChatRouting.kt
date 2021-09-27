package com.shkitter.app.routing.chat

import com.shkitter.app.common.extensions.principalUserIdOrThrow
import com.shkitter.app.common.extensions.receiveOrThrow
import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.common.extensions.scheme
import com.shkitter.app.routing.chat.model.ChatResponse
import com.shkitter.app.routing.chat.model.ChatWithLastMessageResponse
import com.shkitter.app.routing.chat.model.CreateChatRequestDto
import com.shkitter.domain.chat.ChatService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

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
    }
}