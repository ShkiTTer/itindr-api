package com.shkitter.app.routing.profile.model

import com.shkitter.app.common.extensions.throwBadRequestIfError
import com.shkitter.app.common.request.Request
import com.shkitter.domain.validation.AboutMyselfValidationRule
import com.shkitter.domain.validation.TopicIdsValidationRule
import com.shkitter.domain.validation.UserNameValidationRule
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateProfileRequestDto(
    val name: String? = null,
    val aboutMyself: String? = null,
    val topicIds: List<String> = emptyList()
) : Request<CreateProfileRequest>() {

    override fun validateOrThrowError() {
        UserNameValidationRule.validate(name).throwBadRequestIfError()
        AboutMyselfValidationRule.validate(aboutMyself).throwBadRequestIfError()
        TopicIdsValidationRule.validate(topicIds).throwBadRequestIfError()
    }

    override fun toVerified(): CreateProfileRequest = CreateProfileRequest(
        name = name.orEmpty(),
        aboutMyself = aboutMyself,
        topicIds = topicIds.map { UUID.fromString(it) }
    )
}
