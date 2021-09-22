package com.shkitter.app.routing.auth.model

import com.shkitter.domain.token.TokenInfo
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val accessTokenExpiredAt: Long,
    val refreshToken: String,
    val refreshTokenExpiredAt: Long
) {

    companion object {
        fun fromDomain(data: TokenInfo) = TokenResponse(
            accessToken = data.accessToken,
            accessTokenExpiredAt = data.expiredAccess,
            refreshToken = data.refreshToken,
            refreshTokenExpiredAt = data.expiredRefresh
        )
    }
}
