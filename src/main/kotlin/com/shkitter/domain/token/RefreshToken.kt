package com.shkitter.domain.token

import java.time.ZonedDateTime

data class RefreshToken(
    val token: String,
    val expiredAt: ZonedDateTime,
    val userId: String
)
