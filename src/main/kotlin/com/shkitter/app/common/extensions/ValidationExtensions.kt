package com.shkitter.app.common.extensions

import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.validation.ValidationResult

fun ValidationResult.throwBadRequestIfError() = when (this) {
    is ValidationResult.Error -> throw BadRequestException(message)
    else -> Unit
}