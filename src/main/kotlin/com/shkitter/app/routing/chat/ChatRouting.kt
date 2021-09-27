package com.shkitter.app.routing.chat

import com.shkitter.app.common.extensions.principalUserIdOrThrow
import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.common.extensions.scheme
import com.shkitter.app.routing.chat.model.ChatWithLastMessageResponse
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
    }
}