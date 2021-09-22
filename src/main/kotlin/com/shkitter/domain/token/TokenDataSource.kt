package com.shkitter.domain.token

import java.util.*

interface TokenDataSource {
    suspend fun saveTokenInfo(userId: UUID, tokenInfo: TokenInfo)
}