package com.shkitter.app.routing.auth

import com.shkitter.app.common.extensions.receiveOrThrow
import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.routing.auth.model.LoginRequestDto
import com.shkitter.app.routing.auth.model.RegisterRequestDto
import com.shkitter.app.routing.auth.model.TokenResponse
import com.shkitter.domain.auth.AuthService
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureAuthRouting() {

    val authService by inject<AuthService>()

    post(AuthV1.Login.getPath()) {
        val request = call.receiveOrThrow<LoginRequestDto>().validateAndConvertToVerified()
        val tokenInfo = authService.login(email = request.email, password = request.password)
        call.respondSuccess(TokenResponse.fromDomain(tokenInfo))
    }

    post(AuthV1.Register.getPath()) {
        val request = call.receiveOrThrow<RegisterRequestDto>().validateAndConvertToVerified()
        val tokenInfo = authService.register(email = request.email, password = request.password)
        call.respondSuccess(TokenResponse.fromDomain(tokenInfo))
    }
}