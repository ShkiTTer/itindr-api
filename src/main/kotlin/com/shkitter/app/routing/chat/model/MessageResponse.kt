package com.shkitter.app.routing.chat.model

import com.shkitter.app.common.extensions.createFileUrl
import com.shkitter.app.routing.profile.model.ProfileResponse
import com.shkitter.domain.message.model.Message
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Serializable
data class MessageResponse(
    val id: String,
    val text: String,
    val createdAt: String,
    val attachments: List<String>,
    val user: ProfileResponse
) {

    companion object {
        fun fromDomain(data: Message, scheme: String) = MessageResponse(
            id = data.id.toString(),
            text = data.text,
            createdAt = data.createdAt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
            attachments = data.attachments.map { it.file.createFileUrl(scheme = scheme) },
            user = ProfileResponse.fromDomain(data = data.userProfile, scheme = scheme)
        )
    }
}
