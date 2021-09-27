package com.shkitter.data.db.chat

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.message.model.MessageEntity
import com.shkitter.data.db.message.model.MessageTable
import com.shkitter.domain.chat.ChatDataSource
import com.shkitter.domain.chat.model.ChatWithLastMessage
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.mapLazy
import org.jetbrains.exposed.sql.or
import java.util.*

class ChatDataSourceImpl(private val db: Database) : ChatDataSource, DatabaseDataSource {

    override suspend fun getAllChatsForUser(userId: UUID): List<ChatWithLastMessage> = db.dbQuery {
        ChatEntity
            .find {
                (ChatTable.firstUserId eq userId) or (ChatTable.secondUserId eq userId)
            }
            .mapLazy { chat ->
                chat to MessageEntity.find { MessageTable.chatId eq chat.id }
                    .orderBy(MessageTable.createdAt to SortOrder.DESC)
                    .limit(n = 1)
            }
            .mapLazy { chatWithMessage ->
                ChatWithLastMessage(
                    chat = chatWithMessage.first.toDomain(),
                    lastMessage = chatWithMessage.second.firstOrNull()?.toDomain()
                )
            }.toList()
    }
}