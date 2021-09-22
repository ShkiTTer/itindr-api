package com.shkitter.app.routing.auth

import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.routing.auth.model.LoginRequestDto
import com.shkitter.app.routing.auth.model.TokenResponse
import com.shkitter.domain.auth.AuthService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureAuthRouting() {

    val authService by inject<AuthService>()

    post(AuthV1.Login.getPath()) {
        val request = call.receive<LoginRequestDto>().validateAndConvertToVerified()
        val tokenInfo = authService.login(email = request.email, password = request.password)
        call.respondSuccess(TokenResponse.fromDomain(tokenInfo))
    }
}