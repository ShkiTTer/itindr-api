package com.shkitter.domain.message.model

import com.shkitter.domain.attachment.model.NewAttachment
import java.util.*

data class NewMessage(
    val chatId: UUID,
    val userId: UUID,
    val text: String?,
    val attachments: List<NewAttachment>
)
