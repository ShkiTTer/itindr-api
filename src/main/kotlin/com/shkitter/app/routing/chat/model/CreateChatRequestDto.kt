package com.shkitter.app.routing.chat.model

import com.shkitter.app.common.extensions.throwBadRequestIfError
import com.shkitter.app.common.request.Request
import com.shkitter.domain.validation.IdValidationRule
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateChatRequestDto(
    val userId: String? = null
) : Request<CreateChatRequest>() {

    override fun validateOrThrowError() {
        IdValidationRule.validate(userId).throwBadRequestIfError()
    }

    override fun toVerified(): CreateChatRequest = CreateChatRequest(
        userId = UUID.fromString(userId)
    )
}
