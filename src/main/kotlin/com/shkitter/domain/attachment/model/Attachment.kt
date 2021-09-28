package com.shkitter.domain.attachment.model

import java.util.*

data class Attachment(
    val id: UUID,
    val messageId: UUID,
    val file: String
)
