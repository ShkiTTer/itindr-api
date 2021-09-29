package com.shkitter.data.db.chat

import com.shkitter.data.db.message.model.MessageEntity
import com.shkitter.data.db.message.model.MessageTable
import com.shkitter.data.db.user.UserEntity
import com.shkitter.data.db.user.UserTable
import com.shkitter.domain.chat.model.Chat
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object ChatTable : UUIDTable(name = "chats") {
    val firstUserId = reference(name = "first_user_id", foreign = UserTable)
    val secondUserId = reference(name = "second_user_id", foreign = UserTable)
}

class ChatEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ChatEntity>(ChatTable)

    var firstUserId by ChatTable.firstUserId
    var secondUserId by ChatTable.secondUserId

    val firstUser by UserEntity referencedOn ChatTable.firstUserId
    val secondUser by UserEntity referencedOn ChatTable.secondUserId
    val messages by MessageEntity referrersOn MessageTable.chatId

    fun toDomain() = Chat(
        id = id.value,
        firstUser = firstUser.profile.first().toDomain(),
        secondUser = secondUser.profile.first().toDomain()
    )
}