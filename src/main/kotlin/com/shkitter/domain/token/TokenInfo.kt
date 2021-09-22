package com.shkitter.domain.token

data class TokenInfo(
    val accessToken: String,
    val expiredAccess: Long,
    val refreshToken: String,
    val expiredRefresh: Long,
    val userId: String
)
