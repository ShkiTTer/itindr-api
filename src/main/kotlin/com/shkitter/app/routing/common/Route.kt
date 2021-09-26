package com.shkitter.app.routing.common

private const val URL_SEPARATOR = "/"
private const val OPEN_CURLY_BRACE = "{"
private const val CLOSE_CURLY_BRACE = "}"

interface Route {
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

    object Docs : Route {
        override val name: String = "docs"
        override val parent: Route = Mobile
    }
}

interface RouteWithPathParam : Route {
    val paramName: String

    override fun getPath(): String = buildString {
        append(super.getPath())
        append(URL_SEPARATOR)
        append(OPEN_CURLY_BRACE)
        append(paramName)
        append(CLOSE_CURLY_BRACE)
    }
}