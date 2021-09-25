package com.shkitter.domain.auth

import com.shkitter.domain.common.exceptions.ForbiddenException
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.common.exceptions.ResourceAlreadyExistException
import com.shkitter.domain.common.jwt.Jwt
import com.shkitter.domain.common.utils.HashUtil
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.token.TokenDataSource
import com.shkitter.domain.token.TokenInfo
import com.shkitter.domain.user.UserDataSource
import java.util.*

class AuthServiceImpl(
    private val tokenDataSource: TokenDataSource,
    private val userDataSource: UserDataSource,
    private val profileDataSource: ProfileDataSource,
    private val jwt: Jwt
) : AuthService {

    override suspend fun login(email: String, password: String): TokenInfo {
        val user = userDataSource.getUserByEmail(email)
            ?: throw NotFoundException("User with such a pair of email and password was not found")
        val hashPassword = HashUtil.hash(input = password, salt = user.salt)

        if (hashPassword != user.password) throw NotFoundException("User with such a pair of email and password was not found")

        val tokenInfo = jwt.newToken(userId = user.id.toString())
        tokenDataSource.saveTokenInfo(userId = user.id, tokenInfo = tokenInfo)

        return tokenInfo
    }

    override suspend fun register(email: String, password: String): TokenInfo {
        val existUser = userDataSource.getUserByEmail(email)
        if (existUser != null) throw ResourceAlreadyExistException("User with email - '$email' already exist")

        val salt = HashUtil.generateSalt()
        val hashPassword = HashUtil.hash(input = password, salt = salt)
        val newUser = userDataSource.createNewUser(email = email, password = hashPassword, salt = salt)

        val tokenInfo = jwt.newToken(userId = newUser.id.toString())
        tokenDataSource.saveTokenInfo(userId = newUser.id, tokenInfo = tokenInfo)

        profileDataSource.createEmptyProfile(userId = newUser.id)

        return tokenInfo
    }

    override suspend fun logout(userId: UUID) {
        tokenDataSource.clearAllForUser(userId)
    }

    override suspend fun refreshToken(refreshToken: String): TokenInfo {
        val existToken = tokenDataSource.findTokenByValue(refreshToken = refreshToken)
            ?: throw NotFoundException("Session is not found")
        tokenDataSource.removeToken(refreshTokenId = existToken.id)

        if (!jwt.isRefreshTokenNotExpired(existToken)) throw ForbiddenException("Not enough rights to perform the action")

        val newToken = jwt.newToken(userId = existToken.userId.toString())
        tokenDataSource.saveTokenInfo(userId = existToken.userId, tokenInfo = newToken)

        return newToken
    }
}