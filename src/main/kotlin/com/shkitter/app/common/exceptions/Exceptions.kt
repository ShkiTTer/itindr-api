package com.shkitter.app.common.exceptions

data class AuthenticationException(override val message: String? = null) : Exception(message)

data class ForbiddenException(override val message: String? = null) : Exception(message)