package com.shkitter.app.common.extensions

import com.shkitter.app.common.responses.Response
import com.shkitter.domain.common.exceptions.AuthenticationException
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.common.extensions.toUUID
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import java.util.*

suspend fun ApplicationCall.respondError(status: HttpStatusCode, message: String?) =
    this.respond(status, Response.ErrorResponse(message = message))

suspend inline fun <reified T : Any> ApplicationCall.respondSuccess(message: T) =
    this.respond(status = HttpStatusCode.OK, message = message)

suspend fun ApplicationCall.respondSuccessEmpty() =
    this.respond(status = HttpStatusCode.NoContent, message = Unit)

suspend inline fun <reified T : Any> ApplicationCall.receiveOrThrow(): T =
    this.receiveOrNull() ?: throw BadRequestException("Request body is required")

inline fun <reified T> ApplicationCall.getPathParameter(name: String): T =
    this.parameters[name] as? T ?: throw BadRequestException("Path parameter '$name' is required")

inline fun <reified T> ApplicationCall.getPathParameterOrNull(name: String): T? =
    this.parameters[name] as? T

inline fun <reified T> ApplicationCall.getQueryParameter(name: String): T =
    this.request.queryParameters[name] as? T ?: throw BadRequestException("Query parameter '$name' is required")

inline fun <reified T> ApplicationCall.getQueryParameterOrNull(name: String): T? =
    this.request.queryParameters[name] as? T

fun ApplicationCall.principalUserIdOrThrow(): UUID {
    val userId = this.principal<UserIdPrincipal>()?.name
    if (userId.isNullOrBlank()) throw AuthenticationException("You are not authorized")
    return userId.toUUID() ?: throw AuthenticationException("You are not authorized")
}

val ApplicationCall.scheme: String get() = request.local.scheme