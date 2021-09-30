package com.shkitter.app.routing.auth.model

import com.shkitter.domain.common.utils.DateTimeUtils
import com.shkitter.domain.token.TokenInfo
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Serializable
data class TokenResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String
) {

    companion object {
        fun fromDomain(data: TokenInfo) = TokenResponse(
            accessToken = data.accessToken,
            accessTokenExpiredAt = DateTimeUtils.fromSeconds(data.expiredAccess)
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME),
            refreshToken = data.refreshToken,
            refreshTokenExpiredAt = DateTimeUtils.fromSeconds(data.expiredRefresh)
                .format(DateTimeFormatter.ISO_ZONED_DATE_TIME)
        )
    }
}
