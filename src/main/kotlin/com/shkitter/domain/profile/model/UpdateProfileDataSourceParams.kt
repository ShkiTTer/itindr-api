package com.shkitter.domain.profile.model

import java.util.*

data class UpdateProfileDataSourceParams(
    val name: String,
    val aboutMyself: String?,
    val topicIds: List<UUID>,
    val userId: UUID
)
