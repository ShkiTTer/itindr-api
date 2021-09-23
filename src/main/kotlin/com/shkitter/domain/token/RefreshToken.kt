package com.shkitter.domain.token

import java.time.ZonedDateTime
import java.util.*

data class RefreshToken(
    val id: UUID,
    val token: String,
    val expiredAt: ZonedDateTime,
    val userId: UUID
)
