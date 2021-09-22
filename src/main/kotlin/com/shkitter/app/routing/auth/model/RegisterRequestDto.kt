package com.shkitter.app.routing.auth.model

import com.shkitter.app.common.request.Request
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.validation.EmailValidationRule
import com.shkitter.domain.validation.PasswordValidationRule
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    val email: String? = null,
    val password: String? = null
) : Request<RegisterRequest>() {

    override fun validateOrThrowError() = when {
        email.isNullOrBlank() -> throw BadRequestException("Email is required")
        password.isNullOrBlank() -> throw BadRequestException("Password id required")
        !EmailValidationRule(email).validate() -> throw BadRequestException("Email is not correct")
        !PasswordValidationRule(password).validate() -> throw BadRequestException("The password must be 8 characters or more")
        else -> Unit
    }

    override fun toVerified() = RegisterRequest(
        email = email.orEmpty(),
        password = password.orEmpty()
    )
}
