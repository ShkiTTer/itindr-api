package com.shkitter.app.routing.profile

import com.shkitter.app.common.extensions.principalUserIdOrThrow
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*

fun Routing.configureProfile() {
    authenticate {
        route(ProfileV1.getPath()) {
            get {
                val userId = call.principalUserIdOrThrow()
            }
        }
    }
}