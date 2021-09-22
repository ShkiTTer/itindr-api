package com.shkitter.app.common.extensions

import com.shkitter.app.common.responses.Response
import com.shkitter.domain.common.exceptions.BadRequestException
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

suspend fun ApplicationCall.respondError(status: HttpStatusCode, message: String?) =
    this.respond(status, Response.ErrorResponse(message = message))

suspend inline fun <reified T : Any> ApplicationCall.respondSuccess(message: T) =
    this.respond(status = HttpStatusCode.OK, message = message)

suspend inline fun <reified T : Any> ApplicationCall.receiveOrThrow(): T =
    this.receiveOrNull() ?: throw BadRequestException("Request body is required")