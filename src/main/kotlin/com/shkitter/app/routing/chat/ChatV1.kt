package com.shkitter.app.routing.chat

import com.shkitter.app.routing.common.Route
import com.shkitter.app.routing.common.RouteWithPathParam

object ChatV1 : Route {
    override val name: String = "chat"
    override val parent: Route = Route.MobileV1
}

object ChatWithIdV1 : RouteWithPathParam {
    override val name: String = "chat"
    override val parent: Route = Route.MobileV1
    override val paramName: String = "chat_id"

    object Message : Route {
        override val name: String = "message"
        override val parent: Route = ChatWithIdV1

        val limitParameterName = "limit"
        val offsetParameterName = "offset"
    }
}