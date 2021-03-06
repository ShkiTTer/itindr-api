package com.shkitter.data.db.message.model

import com.shkitter.data.db.attachemnt.AttachmentEntity
import com.shkitter.data.db.attachemnt.AttachmentTable
import com.shkitter.data.db.chat.ChatTable
import com.shkitter.data.db.user.UserEntity
import com.shkitter.data.db.user.UserTable
import com.shkitter.domain.common.utils.DateTimeUtils
import com.shkitter.domain.message.model.Message
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object MessageTable : UUIDTable(name = "messages") {
    val text = text(name = "text").nullable().default(null)
    val createdAt = long(name = "created_at").default(DateTimeUtils.getCurrentSeconds())
    val chatId = reference(name = "chat_id", foreign = ChatTable)
    val userId = reference(name = "user_id", foreign = UserTable)
}

class MessageEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<MessageEntity>(MessageTable)

    var text by MessageTable.text
    var createdAt by MessageTable.createdAt
    var chatId by MessageTable.chatId
    var userId by MessageTable.userId

    val user by UserEntity referencedOn MessageTable.userId
    val attachments by AttachmentEntity referrersOn AttachmentTable.messageId

    fun toDomain() = Message(
        id = id.value,
        text = text,
        createdAt = DateTimeUtils.fromSeconds(createdAt),
        chatId = chatId.value,
        userProfile = user.profile.first().toDomain(),
        attachments = attachments.map { it.toDomain() }
    )
}