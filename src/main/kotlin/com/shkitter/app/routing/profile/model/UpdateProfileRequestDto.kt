package com.shkitter.app.routing.profile.model

import com.shkitter.app.common.extensions.throwBadRequestIfError
import com.shkitter.app.common.request.Request
import com.shkitter.domain.validation.AboutMyselfValidationRule
import com.shkitter.domain.validation.TopicIdsValidationRule
import com.shkitter.domain.validation.UserNameValidationRule
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateProfileRequestDto(
    val name: String? = null,
    val aboutMyself: String? = null,
    val topics: List<String> = emptyList()
) : Request<UpdateProfileRequest>() {

    override fun validateOrThrowError() {
        UserNameValidationRule.validate(name).throwBadRequestIfError()
        AboutMyselfValidationRule.validate(aboutMyself).throwBadRequestIfError()
        TopicIdsValidationRule.validate(topics).throwBadRequestIfError()
    }

    override fun toVerified(): UpdateProfileRequest = UpdateProfileRequest(
        name = name.orEmpty(),
        aboutMyself = aboutMyself,
        topicIds = topics.map { UUID.fromString(it) }
    )
}
