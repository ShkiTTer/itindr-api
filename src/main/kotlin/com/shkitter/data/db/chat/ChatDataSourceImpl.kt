package com.shkitter.data.db.chat

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.chat.ChatDataSource
import com.shkitter.domain.chat.model.Chat
import com.shkitter.domain.message.model.Message
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.or
import java.util.*

class ChatDataSourceImpl(private val db: Database) : ChatDataSource, DatabaseDataSource {

    override suspend fun getAllChatsForUser(userId: UUID): List<Chat> = db.dbQuery {
        ChatEntity
            .find { (ChatTable.firstUserId eq userId) or (ChatTable.secondUserId eq userId) }
            .map { it.toDomain() }
    }

    override suspend fun createChat(firstUserId: UUID, secondUserId: UUID): Chat = db.dbQuery {
        ChatEntity.new {
            this.firstUserId = UserEntity[firstUserId].id
            this.secondUserId = UserEntity[secondUserId].id
        }.toDomain()
    }

    override suspend fun getChatById(chatId: UUID): Chat? = db.dbQuery {
        ChatEntity.findById(chatId)?.toDomain()
    }

    override suspend fun getChatMessages(chatId: UUID, limit: Int, offset: Int): List<Message> = db.dbQuery {
        ChatEntity[chatId].messages.limit(n = limit, offset = offset.toLong()).map { it.toDomain() }
    }
}