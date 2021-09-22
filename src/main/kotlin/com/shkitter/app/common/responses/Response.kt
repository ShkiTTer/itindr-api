package com.shkitter.app.common.responses

import kotlinx.serialization.Serializable

sealed interface Response {
    @Serializable
    data class ErrorResponse(val message: String? = null): Response
}