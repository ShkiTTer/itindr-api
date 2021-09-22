package com.shkitter.app.plugins

import com.shkitter.app.common.exceptions.AuthenticationException
import com.shkitter.app.common.exceptions.ForbiddenException
import com.shkitter.app.common.extensions.respondError
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> {
            call.respondError(HttpStatusCode.BadRequest, it.message)
        }

        exception<NotFoundException> {
            call.respondError(HttpStatusCode.NotFound, it.message)
        }

        exception<AuthenticationException> {
            call.respondError(HttpStatusCode.Unauthorized, it.message)
        }

        exception<ForbiddenException> {
            call.respondError(HttpStatusCode.Forbidden, it.message)
        }

        exception<Throwable> {
            call.respondError(HttpStatusCode.InternalServerError, it.message)
        }
    }
}