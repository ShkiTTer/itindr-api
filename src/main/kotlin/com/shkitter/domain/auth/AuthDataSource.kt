package com.shkitter.domain.auth

import com.shkitter.domain.token.TokenInfo
import java.util.*

interface AuthDataSource {
    suspend fun saveTokenInfo(userId: UUID, tokenInfo: TokenInfo)
}