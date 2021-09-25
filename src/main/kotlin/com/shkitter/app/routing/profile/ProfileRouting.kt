package com.shkitter.app.routing.profile

import com.shkitter.app.common.extensions.principalUserIdOrThrow
import com.shkitter.app.common.extensions.receiveOrThrow
import com.shkitter.app.common.extensions.respondSuccess
import com.shkitter.app.routing.profile.model.CreateProfileRequest
import com.shkitter.app.routing.profile.model.CreateProfileRequestDto
import com.shkitter.app.routing.profile.model.ProfileWithTopicsResponse
import com.shkitter.domain.profile.ProfileService
import com.shkitter.domain.profile.model.CreateProfileServiceParams
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

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
                val newProfile =
                    profileService.createProfile(
                        params = getCreateProfileParams(
                            request = createProfileRequest,
                            userId = userId
                        )
                    )
                call.respondSuccess(ProfileWithTopicsResponse.fromDomain(newProfile))
            }

            patch {
                val userId = call.principalUserIdOrThrow()
                val profileRequest = call.receiveOrThrow<CreateProfileRequestDto>().validateAndConvertToVerified()
                val updatedProfile =
                    profileService.updateProfile(
                        params = getCreateProfileParams(
                            request = profileRequest,
                            userId = userId
                        )
                    )
                call.respondSuccess(ProfileWithTopicsResponse.fromDomain(updatedProfile))
            }
        }
    }
}

private fun getCreateProfileParams(request: CreateProfileRequest, userId: UUID) = CreateProfileServiceParams(
    name = request.name,
    aboutMyself = request.aboutMyself,
    topicIds = request.topicIds,
    userId = userId
)