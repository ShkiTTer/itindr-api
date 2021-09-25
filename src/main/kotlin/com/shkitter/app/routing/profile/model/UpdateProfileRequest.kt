package com.shkitter.app.routing.profile.model

import java.util.*

data class UpdateProfileRequest(
    val name: String,
    val aboutMyself: String?,
    val topicIds: List<UUID>
)
