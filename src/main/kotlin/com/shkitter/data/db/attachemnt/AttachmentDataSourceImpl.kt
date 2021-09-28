package com.shkitter.data.db.attachemnt

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.message.model.MessageEntity
import com.shkitter.domain.attachment.AttachmentDataSource
import com.shkitter.domain.attachment.model.Attachment
import org.jetbrains.exposed.sql.Database
import java.util.*

class AttachmentDataSourceImpl(
    private val db: Database
) : AttachmentDataSource, DatabaseDataSource {

    override suspend fun createAttachment(messageId: UUID, filePath: String): Attachment = db.dbQuery {
        AttachmentEntity.new {
            this.messageId = MessageEntity[messageId].id
            this.file = filePath
        }.toDomain()
    }
}