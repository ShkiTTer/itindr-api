package com.shkitter.app.routing.profile.model

import com.shkitter.app.common.extensions.createFileUrl
import com.shkitter.domain.profile.model.Profile
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponse(
    val userId: String,
    val name: String,
    val aboutMyself: String?,
    val avatar: String?
) {

    companion object {
        fun fromDomain(data: Profile, scheme: String) = ProfileResponse(
            userId = data.userId.toString(),
            name = data.name,
            aboutMyself = data.aboutMyself,
            avatar = data.avatar?.createFileUrl(scheme = scheme)
        )
    }
}
