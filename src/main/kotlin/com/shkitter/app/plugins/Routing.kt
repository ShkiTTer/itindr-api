package com.shkitter.app.plugins

import com.shkitter.app.common.extensions.getConfigProperty
import com.shkitter.app.routing.auth.configureAuthRouting
import com.shkitter.app.routing.common.Route
import com.shkitter.app.routing.profile.configureProfile
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

private const val KEY_ROOT_PATH = "ktor.deployment.rootPath"

fun Application.configureRouting() {
    val rootPath = getConfigProperty(KEY_ROOT_PATH)

    routing {
        get(Route.Docs.getPath()) {
            call.respondRedirect(
                url = "${rootPath}docs/index.html?url=${rootPath}static/tinder-api.yaml",
                permanent = true
            )
        }

        configureAuthRouting()
        configureProfile()
    }
}
