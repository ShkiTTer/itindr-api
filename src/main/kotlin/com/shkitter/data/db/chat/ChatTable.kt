package com.shkitter.data.db.chat

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

    fun toDomain() = Chat(
        id = id.value,
        firstUserId = firstUserId.value,
        secondUserId = secondUserId.value
    )
}