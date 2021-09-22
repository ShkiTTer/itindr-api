package com.shkitter.domain.common.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import com.shkitter.domain.common.utils.DateTimeUtils
import com.shkitter.domain.token.TokenInfo
import java.security.SecureRandom
import java.util.*

class Jwt(
    secret: String,
    private val accessTokenValiditySeconds: Long,
    private val refreshTokenValiditySeconds: Long,
    private val issuer: String
) {
    companion object {
        private const val REFRESH_LENGTH = 64
        private const val KEY_CLAIM_USER = "user"
    }

    private val random = SecureRandom()
    private val algorithm = Algorithm.HMAC256(Base64.getDecoder().decode(secret))

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    private fun getExpired(validity: Long) = DateTimeUtils.getCurrentSeconds() + validity

    private fun generateAccessToken(userId: String, expired: Long): String = JWT.create()
        .withExpiresAt(Date.from(DateTimeUtils.instantFromSeconds(expired)))
        .withSubject(userId)
        .withIssuer(issuer)
        .withClaim(KEY_CLAIM_USER, userId)
        .sign(algorithm)

    private fun generateRefreshToken(): String {
        val bytes = ByteArray(REFRESH_LENGTH)
        random.nextBytes(bytes)

        return Base64.getEncoder().encodeToString(bytes)
    }

    fun newToken(userId: String): TokenInfo {
        val expiredAccess = getExpired(accessTokenValiditySeconds)
        val expiredRefresh = getExpired(refreshTokenValiditySeconds)

        return TokenInfo(
            accessToken = generateAccessToken(userId, expiredAccess),
            expiredAccess = expiredAccess,
            refreshToken = generateRefreshToken(),
            expiredRefresh = expiredRefresh,
            userId = userId
        )
    }

    fun getUserDataFromPayload(payload: Payload): String? = payload.claims?.get(KEY_CLAIM_USER)?.asString()
}