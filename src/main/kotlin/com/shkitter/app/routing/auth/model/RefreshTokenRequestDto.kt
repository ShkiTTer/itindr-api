package com.shkitter.app.routing.auth.model

import com.shkitter.app.common.request.Request
import com.shkitter.domain.common.exceptions.BadRequestException
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequestDto(
    val refreshToken: String? = null
) : Request<RefreshTokenRequest>() {

    override fun validateOrThrowError() = when {
        refreshToken.isNullOrBlank() -> throw BadRequestException("Refresh token is required")
        else -> Unit
    }

    override fun toVerified() = RefreshTokenRequest(
        refreshToken = refreshToken.orEmpty()
    )
}
