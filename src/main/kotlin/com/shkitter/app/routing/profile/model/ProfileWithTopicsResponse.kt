package com.shkitter.app.routing.profile.model

import com.shkitter.app.common.extensions.createFileUrl
import com.shkitter.app.routing.topic.model.TopicResponse
import com.shkitter.domain.profile.model.ProfileWithTopics
import kotlinx.serialization.Serializable

@Serializable
data class ProfileWithTopicsResponse(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?,
    val topics: List<TopicResponse>
) {

    companion object {
        fun fromDomain(data: ProfileWithTopics, scheme: String) = ProfileWithTopicsResponse(
            userId = data.userId.toString(),
            name = data.name,
            aboutMyself = data.aboutMyself,
            avatar = data.avatar?.createFileUrl(scheme),
            topics = data.topics.map { TopicResponse.fromDomain(it) }
        )
    }
}
