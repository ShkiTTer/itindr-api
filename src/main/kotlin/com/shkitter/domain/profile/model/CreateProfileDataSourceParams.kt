package com.shkitter.domain.profile.model

import java.util.*

data class CreateProfileDataSourceParams(
    val name: String,
    val aboutMyself: String?,
    val topicIds: List<UUID>,
    val userId: UUID,
    val avatar: String?
)
