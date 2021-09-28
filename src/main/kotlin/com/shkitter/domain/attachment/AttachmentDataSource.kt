package com.shkitter.domain.attachment

import com.shkitter.domain.attachment.model.Attachment
import java.util.*

interface AttachmentDataSource {
    suspend fun createAttachment(messageId: UUID, filePath: String): Attachment
}