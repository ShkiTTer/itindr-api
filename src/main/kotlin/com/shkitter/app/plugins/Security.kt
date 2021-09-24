package com.shkitter.app.plugins

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.shkitter.app.common.extensions.getConfigProperty
import com.shkitter.app.common.extensions.getConfigPropertyOrNull
import com.shkitter.app.routing.auth.AuthV1
import com.shkitter.domain.common.exceptions.AuthenticationException
import com.shkitter.domain.common.jwt.Jwt
import com.shkitter.domain.common.utils.SystemEnvVariablesUtil
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.request.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.inject

private const val KEY_JWT_ISSUER = "jwt.issuer"
private const val KEY_JWT_SECRET = "jwt.secret"

fun Application.configureSecurity() {
    val issuer = getConfigPropertyOrNull(KEY_JWT_ISSUER).orEmpty()
    val secret = getConfigProperty(KEY_JWT_SECRET)

    val jwt by inject<Jwt> {
        parametersOf(secret, issuer)
    }

    install(Authentication) {
        jwt {
            verifier(jwt.verifier)
            this.challenge { scheme, _ ->
                val errorMessage = call.request.headers["Authorization"]?.let { token ->
                    when {
                        token.isBlank() -> "Authorization token empty"
                        else -> try {
                            val clearToken = token.replace(scheme, "").trim()
                            jwt.verifier.verify(clearToken)
                            ""
                        } catch (e: Exception) {
                            when (e) {
                                is TokenExpiredException ->
                                    if (call.request.path().contains(AuthV1.Refresh.getPath())
                                    ) {
                                        ""
                                    } else {
                                        e.message
                                    }
                                is JWTVerificationException -> e.message
                                else -> "Unknown token error"
                            }
                        }
                    }
                } ?: "No authorization header"

                if (errorMessage.isNotEmpty()) throw AuthenticationException(errorMessage)
            }

            validate { credential ->
                jwt.getUserDataFromPayload(credential.payload)?.let { UserIdPrincipal(it) }
            }
        }
    }
}
