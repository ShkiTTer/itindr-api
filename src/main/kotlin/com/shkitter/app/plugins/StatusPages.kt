package com.shkitter.app.plugins

import com.shkitter.app.common.extensions.respondError
import com.shkitter.domain.common.exceptions.AuthenticationException
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.common.exceptions.ForbiddenException
import com.shkitter.domain.common.exceptions.NotFoundException
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

        exception<Exception> {
            call.respondError(HttpStatusCode.InternalServerError, it.message)
        }
    }
}