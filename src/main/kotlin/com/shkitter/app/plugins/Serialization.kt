package com.shkitter.app.plugins

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(json = Json {
            coerceInputValues = true
            encodeDefaults = true
        })
    }
}
