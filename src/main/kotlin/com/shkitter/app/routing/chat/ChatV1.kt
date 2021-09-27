package com.shkitter.app.routing.chat

import com.shkitter.app.routing.common.Route

object ChatV1 : Route {
    override val name: String = "chat"
    override val parent: Route = Route.MobileV1
}