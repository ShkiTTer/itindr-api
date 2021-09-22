package com.shkitter.app.plugins

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureStatic() {
    routing {
        static("/static") {
            resource("tinder-api.yaml", "/swagger/tinder-api.yaml")
        }
    }
}