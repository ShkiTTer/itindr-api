package com.shkitter.app.plugins

import com.shkitter.app.common.extensions.respondError
import com.shkitter.domain.common.exceptions.*
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.common.exceptions.NotFoundException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.util.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> {
            log.error(it)
            call.respondError(HttpStatusCode.BadRequest, it.message)
        }

        exception<NotFoundException> {
            log.error(it)
            call.respondError(HttpStatusCode.NotFound, it.message)
        }

        exception<AuthenticationException> {
            log.error(it)
            call.respondError(HttpStatusCode.Unauthorized, it.message)
        }

        exception<ForbiddenException> {
            log.error(it)
            call.respondError(HttpStatusCode.Forbidden, it.message)
        }

        exception<ResourceAlreadyExistException> {
            log.error(it)
            call.respondError(HttpStatusCode.Conflict, it.message)
        }

        exception<Exception> {
            log.error(it)
            call.respondError(HttpStatusCode.InternalServerError, it.message)
        }
    }
}