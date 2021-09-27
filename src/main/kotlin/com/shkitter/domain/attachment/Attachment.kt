package com.shkitter.domain.attachment

import java.util.*

data class Attachment(
    val id: UUID,
    val messageId: UUID,
    val file: String
)
