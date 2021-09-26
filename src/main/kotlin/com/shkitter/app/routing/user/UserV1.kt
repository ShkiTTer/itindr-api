package com.shkitter.app.routing.user

import com.shkitter.app.routing.common.Route
import com.shkitter.app.routing.common.RouteWithPathParam

object UserV1 : Route {
    override val name: String = "user"
    override val parent: Route = Route.MobileV1

    object Feed : Route {
        override val name: String = "feed"
        override val parent: Route = UserV1
    }
}

object UserV1WithId : RouteWithPathParam {
    override val name: String = "user"
    override val parent: Route = Route.MobileV1
    override val paramName: String = "userId"

    object Like : Route {
        override val name: String = "like"
        override val parent: Route = UserV1WithId
    }

    object Dislike : Route {
        override val name: String = "dislike"
        override val parent: Route = UserV1WithId
    }
}