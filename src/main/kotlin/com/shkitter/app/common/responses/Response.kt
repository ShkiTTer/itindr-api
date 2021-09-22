package com.shkitter.app.common.responses

sealed interface Response {
    data class ErrorResponse(val message: String? = null): Response
}