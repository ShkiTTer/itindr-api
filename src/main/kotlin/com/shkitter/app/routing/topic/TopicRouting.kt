package com.shkitter.app.routing.topic

import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.routing.topic.model.TopicResponse
import com.shkitter.domain.topic.TopicService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureTopic() {

    val topicService by inject<TopicService>()

    authenticate {
        get(TopicV1.getPath()) {
            call.respondSuccess(topicService.getAll().map { TopicResponse.fromDomain(it) })
        }
    }
}