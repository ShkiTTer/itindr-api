package com.shkitter.domain.chat.model

import java.util.*

data class Chat(
    val id: UUID,
    val firstUserId: UUID,
    val secondUserId: UUID
)
