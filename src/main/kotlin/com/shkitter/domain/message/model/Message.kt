package com.shkitter.domain.message.model

import com.shkitter.domain.attachment.Attachment
import java.time.ZonedDateTime
import java.util.*

data class Message(
    val id: UUID,
    val text: String,
    val createdAt: ZonedDateTime,
    val chatId: UUID,
    val userId: UUID,
    val attachments: List<Attachment>
)
