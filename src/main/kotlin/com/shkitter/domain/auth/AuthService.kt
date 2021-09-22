package com.shkitter.domain.auth

import com.shkitter.domain.token.TokenInfo

interface AuthService {
    suspend fun login(email: String, password: String): TokenInfo
}