package com.shkitter.app.routing.auth

import com.shkitter.app.common.locations.MobileApi
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

@KtorExperimentalLocationsAPI
fun Routing.configureAuthRouting() {
    get<MobileApi.AuthV1.LoginV1> {
        call.respond("Success login")
    }
}