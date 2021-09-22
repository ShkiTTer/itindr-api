package com.shkitter.app.common.extensions

import com.shkitter.app.common.responses.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

suspend fun ApplicationCall.respondError(status: HttpStatusCode, message: String?) {
    this.respond(status, Response.ErrorResponse(message = message))
}