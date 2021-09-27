package com.shkitter.data.db.attachemnt

import com.shkitter.data.db.message.model.MessageTable
import com.shkitter.domain.attachment.Attachment
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

object AttachmentTable : UUIDTable(name = "attachments") {
    val file = varchar(name = "file", length = 150)
    val messageId = reference(name = "message_id", foreign = MessageTable, onDelete = ReferenceOption.CASCADE)
}

class AttachmentEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AttachmentEntity>(AttachmentTable)

    var file by AttachmentTable.file
    var messageId by AttachmentTable.messageId

    fun toDomain() = Attachment(
        id = id.value,
        messageId = messageId.value,
        file = file
    )
}