package com.shkitter.domain.chat.model

import com.shkitter.domain.message.model.Message

data class ChatWithLastMessage(
    val chat: Chat,
    val lastMessage: Message?
)
