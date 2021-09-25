package com.shkitter.app.routing.topic

import com.shkitter.app.routing.common.Route

object TopicV1 : Route {
    override val name: String = "topic"
    override val parent: Route = Route.MobileV1
}