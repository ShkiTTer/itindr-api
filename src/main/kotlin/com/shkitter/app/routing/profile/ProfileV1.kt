package com.shkitter.app.routing.profile

import com.shkitter.app.routing.common.Route

object ProfileV1 : Route {
    override val name: String = "profile"
    override val parent: Route = Route.MobileV1

    object Avatar : Route {
        override val name: String = "avatar"
        override val parent: Route = ProfileV1
    }

}