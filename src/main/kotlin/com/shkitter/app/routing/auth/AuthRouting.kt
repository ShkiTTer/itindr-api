package com.shkitter.app.routing.auth

import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.configureAuthRouting() {
    get(AuthV1.Login.getPath()) {
        call.respond("Success")
    }
}