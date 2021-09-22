package com.shkitter.app.routing.auth

import com.shkitter.app.routing.common.Route

object AuthV1 : Route {
    override val name: String = "auth"
    override val parent: Route = Route.MobileV1

    object Login : Route {
        override val name: String = "login"
        override val parent: Route = AuthV1
    }

    object Register : Route {
        override val name: String = "register"
        override val parent: Route = AuthV1
    }

    object Logout : Route {
        override val name: String = "logout"
        override val parent: Route = AuthV1
    }
}