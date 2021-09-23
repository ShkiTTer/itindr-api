package com.shkitter.domain.common.utils

import java.util.concurrent.TimeUnit

object SystemEnvVariablesUtil {
    private const val KEY_ACCESS_TOKEN_VALIDITY_IN_HOURS = "TINDER_ACCESS_TOKEN_VALIDITY_IN_HOURS"
    private const val KEY_REFRESH_TOKEN_VALIDITY_IN_DAYS = "TINDER_REFRESH_TOKEN_VALIDITY_IN_DAYS"

    private const val KEY_JDBC_URL = "TINDER_JDBC_URL"
    private const val KEY_DATABASE_USER = "TINDER_DATABASE_USER"
    private const val KEY_DATABASE_PASSWORD = "TINDER_DATABASE_PASSWORD"

    private val DEFAULT_ACCESS_TOKEN_VALIDITY_IN_SECONDS = TimeUnit.HOURS.toSeconds(1)
    private val DEFAULT_REFRESH_TOKEN_VALIDITY_IN_SECONDS = TimeUnit.DAYS.toSeconds(7)

    val accessTokenValidityInSeconds =
        System.getenv(KEY_ACCESS_TOKEN_VALIDITY_IN_HOURS)?.toLongOrNull()?.let { TimeUnit.HOURS.toSeconds(it) }
            ?: DEFAULT_ACCESS_TOKEN_VALIDITY_IN_SECONDS

    val refreshTokenValidityInSeconds =
        System.getenv(KEY_REFRESH_TOKEN_VALIDITY_IN_DAYS)?.toLongOrNull()?.let { TimeUnit.DAYS.toSeconds(it) }
            ?: DEFAULT_REFRESH_TOKEN_VALIDITY_IN_SECONDS

    val jdbcUrl: String = System.getenv(KEY_JDBC_URL)
    val dbUser: String = System.getenv(KEY_DATABASE_USER)
    val dbPassword: String = System.getenv(KEY_DATABASE_PASSWORD)
}