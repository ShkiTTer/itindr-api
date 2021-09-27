package com.shkitter.domain.chat.model

import com.shkitter.domain.profile.model.Profile
import java.util.*

data class Chat(
    val id: UUID,
    val firstUser: Profile,
    val secondUser: Profile
)
