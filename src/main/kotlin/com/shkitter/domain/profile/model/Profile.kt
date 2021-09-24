package com.shkitter.domain.profile.model

import java.util.*

data class Profile(
    val id: UUID,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val userId: UUID
)
