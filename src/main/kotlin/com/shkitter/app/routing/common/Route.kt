package com.shkitter.app.routing.common

interface Route {
    private companion object {
        const val URL_SEPARATOR = "/"
    }

    val parent: Route?
    val name: String

    fun getPath(): String = buildString {
        append(parent?.getPath().orEmpty())
        append(URL_SEPARATOR)
        append(name)
    }

    object Api : Route {
        override val name: String = "api"
        override val parent: Route? = null
    }

    object Mobile : Route {
        override val name: String = "mobile"
        override val parent: Route = Api
    }

    object MobileV1 : Route {
        override val name: String = "v1"
        override val parent: Route = Mobile
    }
}