package com.shkitter.app.plugins

import com.shkitter.app.jwt.Jwt
import com.shkitter.domain.common.utils.SystemEnvVariablesUtil
import com.shkitter.app.common.extensions.getConfigProperty
import com.shkitter.app.common.extensions.getConfigPropertyOrNull
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject

private const val KEY_JWT_ISSUER = "jwt.issuer"
private const val KEY_JWT_SECRET = "jwt.secret"

fun Application.configureSecurity() {
    val issuer = getConfigPropertyOrNull(KEY_JWT_ISSUER).orEmpty()
    val secret = getConfigProperty(KEY_JWT_SECRET)
    val accessTokenValiditySeconds = SystemEnvVariablesUtil.accessTokenValidityInSeconds
    val refreshTokenValidity = SystemEnvVariablesUtil.refreshTokenValidityInSeconds

    val jwt by inject<Jwt> {
        parametersOf(secret, issuer, accessTokenValiditySeconds, refreshTokenValidity)
    }

    authentication {
        jwt {
            verifier(jwt.verifier)

            validate { credential ->
                jwt.getUserDataFromPayload(credential)?.let { UserIdPrincipal(it) }
            }
        }
    }

}
