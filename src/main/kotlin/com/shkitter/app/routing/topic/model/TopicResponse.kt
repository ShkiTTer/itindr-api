package com.shkitter.app.routing.topic.model

import com.shkitter.domain.topic.model.Topic
import kotlinx.serialization.Serializable

@Serializable
data class TopicResponse(
    val id: String,
    val title: String
) {

    companion object {
        fun fromDomain(data: Topic) = TopicResponse(
            id = data.id.toString(),
            title = data.title
        )
    }
}
