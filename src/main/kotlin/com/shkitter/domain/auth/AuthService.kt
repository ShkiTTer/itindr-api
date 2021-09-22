package com.shkitter.domain.auth

import com.shkitter.domain.token.TokenInfo
import java.util.*

interface AuthService {
    suspend fun login(email: String, password: String): TokenInfo
    suspend fun register(email: String, password: String): TokenInfo
    suspend fun logout(userId: UUID)
    suspend fun refreshToken(refreshToken: String): TokenInfo
}