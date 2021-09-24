package com.shkitter.app.routing.profile.model

import com.shkitter.app.routing.topic.model.TopicResponse
import com.shkitter.domain.profile.model.ProfileWithTopics
import kotlinx.serialization.Serializable

@Serializable
data class ProfileWithTopicsResponse(
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: List<TopicResponse>
) {

    companion object {
        fun fromDomain(data: ProfileWithTopics) = ProfileWithTopicsResponse(
            name = data.name,
            aboutMyself = data.aboutMyself,
            avatar = data.avatar,
            topics = data.topics.map { TopicResponse.fromDomain(it) }
        )
    }
}