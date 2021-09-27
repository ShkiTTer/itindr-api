package com.shkitter.domain.message

import com.shkitter.domain.message.model.Message
import java.util.*

interface MessageDataSource {
    suspend fun getLastMessageForChat(chatId: UUID): Message?
}