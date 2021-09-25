package com.shkitter.app.routing.profile

import com.shkitter.app.common.extensions.principalUserIdOrThrow
import com.shkitter.app.common.extensions.receiveOrThrow
import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.routing.profile.model.CreateProfileRequestDto
import com.shkitter.app.routing.profile.model.ProfileWithTopicsResponse
import com.shkitter.domain.profile.ProfileService
import com.shkitter.domain.profile.model.CreateProfileServiceParams
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureProfile() {

    val profileService by inject<ProfileService>()

    authenticate {
        route(ProfileV1.getPath()) {
            get {
                val userId = call.principalUserIdOrThrow()
                val fullProfile = profileService.getFullProfileByUserId(userId = userId)
                call.respondSuccess(ProfileWithTopicsResponse.fromDomain(fullProfile))
            }

            post {
                val userId = call.principalUserIdOrThrow()
                val createProfileRequest = call.receiveOrThrow<CreateProfileRequestDto>().validateAndConvertToVerified()
                val newProfile = profileService.createProfile(
                    params = CreateProfileServiceParams(
                        name = createProfileRequest.name,
                        aboutMyself = createProfileRequest.aboutMyself,
                        topicIds = createProfileRequest.topicIds,
                        userId = userId
                    )
                )
                call.respondSuccess(ProfileWithTopicsResponse.fromDomain(newProfile))
            }
        }
    }
}