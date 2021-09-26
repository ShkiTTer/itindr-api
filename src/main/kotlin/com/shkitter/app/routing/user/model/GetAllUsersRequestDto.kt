package com.shkitter.app.routing.user.model

import com.shkitter.app.common.extensions.throwBadRequestIfError
import com.shkitter.app.common.request.Request
import com.shkitter.domain.validation.LimitValidationRule
import com.shkitter.domain.validation.OffsetValidationRule
import kotlinx.serialization.Serializable

@Serializable
data class GetAllUsersRequestDto(
    val limit: Int? = null,
    val offset: Int? = null
) : Request<GetAllUsersRequest>() {

    override fun validateOrThrowError() {
        LimitValidationRule.validate(limit).throwBadRequestIfError()
        OffsetValidationRule.validate(offset).throwBadRequestIfError()
    }

    override fun toVerified(): GetAllUsersRequest = GetAllUsersRequest(
        limit = limit ?: 0,
        offset = offset ?: 0
    )
}
