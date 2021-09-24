package com.shkitter.domain.profile.model

import com.shkitter.domain.topic.model.Topic
import java.util.*

data class ProfileWithTopics(
    val id: UUID,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val userId: UUID,
    val topics: List<Topic>
)
