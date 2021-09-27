package com.shkitter.data.db.chat

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.domain.chat.ChatDataSource
import com.shkitter.domain.chat.model.Chat
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.or
import java.util.*

class ChatDataSourceImpl(private val db: Database) : ChatDataSource, DatabaseDataSource {

    override suspend fun getAllChatsForUser(userId: UUID): List<Chat> = db.dbQuery {
        ChatEntity
            .find {
                (ChatTable.firstUserId eq userId) or (ChatTable.secondUserId eq userId)
            }
            .map { it.toDomain() }
    }
}