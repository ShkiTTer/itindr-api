package com.shkitter.domain.auth

import com.shkitter.domain.common.jwt.Jwt
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.common.utils.HashUtil
import com.shkitter.domain.token.TokenInfo
import com.shkitter.domain.user.UserDataSource

class AuthServiceImpl(
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
    private val jwt: Jwt
) : AuthService {

    override suspend fun login(email: String, password: String): TokenInfo {
        val user = userDataSource.getUserByEmail(email)
            ?: throw NotFoundException("User with such a pair of email and password was not found")
        val hashPassword = HashUtil.hash(input = password, salt = user.salt)

        if (hashPassword != user.password) throw NotFoundException("User with such a pair of email and password was not found")

        val tokenInfo = jwt.newToken(userId = user.id.toString())
        authDataSource.saveTokenInfo(userId = user.id, tokenInfo = tokenInfo)

        return tokenInfo
    }
}