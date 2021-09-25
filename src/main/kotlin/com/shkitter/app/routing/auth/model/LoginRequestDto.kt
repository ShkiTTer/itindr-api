package com.shkitter.app.routing.auth.model

import com.shkitter.app.common.extensions.throwBadRequestIfError
import com.shkitter.app.common.request.Request
import com.shkitter.domain.validation.EmailValidationRule
import com.shkitter.domain.validation.PasswordValidationRule
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    val email: String? = null,
    val password: String? = null
) : Request<LoginRequest>() {

    override fun validateOrThrowError() {
        EmailValidationRule.validate(email).throwBadRequestIfError()
        PasswordValidationRule.validate(password).throwBadRequestIfError()
    }

    override fun toVerified() = LoginRequest(
        email = email.orEmpty(),
        password = password.orEmpty()
    )
}
