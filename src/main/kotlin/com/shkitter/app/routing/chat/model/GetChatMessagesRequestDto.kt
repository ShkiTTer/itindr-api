package com.shkitter.app.routing.chat.model

import com.shkitter.app.common.extensions.throwBadRequestIfError
import com.shkitter.app.common.request.Request
import com.shkitter.domain.validation.LimitValidationRule
import com.shkitter.domain.validation.OffsetValidationRule
import kotlinx.serialization.Serializable

@Serializable
data class GetChatMessagesRequestDto(
    val limit: Int? = null,
    val offset: Int? = null
) : Request<GetChatMessagesRequest>() {

    override fun validateOrThrowError() {
        LimitValidationRule.validate(limit).throwBadRequestIfError()
        OffsetValidationRule.validate(offset).throwBadRequestIfError()
    }

    override fun toVerified(): GetChatMessagesRequest = GetChatMessagesRequest(
        limit = limit ?: 0,
        offset = offset ?: 0
    )
}
