package com.shkitter.app.common.locations

import io.ktor.locations.*

@KtorExperimentalLocationsAPI
@Location("/api/mobile")
object MobileApi {

    @Location("v1/auth")
    data class AuthV1(val parent: MobileApi) {
        @Location("login")
        data class LoginV1(val parent: AuthV1)

        @Location("register")
        data class RegisterV1(val parent: AuthV1)
    }

    @Location("docs")
    data class Docs(val parent: MobileApi)
}