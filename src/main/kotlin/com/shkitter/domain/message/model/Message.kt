package com.shkitter.domain.message.model

import com.shkitter.domain.attachment.model.Attachment
import com.shkitter.domain.profile.model.Profile
import java.time.ZonedDateTime
import java.util.*

data class Message(
    val id: UUID,
    val text: String,
    val createdAt: ZonedDateTime,
    val chatId: UUID,
    val userProfile: Profile,
    val attachments: List<Attachment>
)
