package com.shkitter.domain.token

import java.util.*

interface TokenDataSource {
    suspend fun saveTokenInfo(userId: UUID, tokenInfo: TokenInfo)
    suspend fun clearAllForUser(userId: UUID)
    suspend fun removeToken(refreshTokenId: UUID)
    suspend fun findTokenByValue(refreshToken: String): RefreshToken?
}