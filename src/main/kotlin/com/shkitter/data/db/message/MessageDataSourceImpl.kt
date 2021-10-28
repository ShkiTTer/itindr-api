package com.shkitter.data.db.message

import com.shkitter.data.db.chat.ChatEntity
import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.message.model.MessageEntity
import com.shkitter.data.db.message.model.MessageTable
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.common.utils.DateTimeUtils
import com.shkitter.domain.message.MessageDataSource
import com.shkitter.domain.message.model.Message
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import java.util.*

class MessageDataSourceImpl(private val db: Database) : MessageDataSource, DatabaseDataSource {

    override suspend fun getLastMessageForChat(chatId: UUID): Message? = db.dbQuery {
        MessageEntity
            .find { MessageTable.chatId eq chatId }
            .orderBy(MessageTable.createdAt to SortOrder.DESC)
            .limit(n = 1)
            .firstOrNull()
            ?.toDomain()
    }

    override suspend fun createMessage(userId: UUID, chatId: UUID, text: String?): Message = db.dbQuery {
        MessageEntity.new {
            this.userId = UserEntity[userId].id
            this.chatId = ChatEntity[chatId].id
            this.text = text
            this.createdAt = DateTimeUtils.getCurrentSeconds()
        }.toDomain()
    }
}