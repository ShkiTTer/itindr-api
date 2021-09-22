package com.shkitter.domain.common.exceptions

data class AuthenticationException(override val message: String? = null) : Exception(message)

data class ForbiddenException(override val message: String? = null) : Exception(message)

data class BadRequestException(override val message: String? = null) : Exception(message)

data class NotFoundException(override val message: String? = null) : Exception(message)

data class ResourceAlreadyExistException(override val message: String? = null) : Exception(message)