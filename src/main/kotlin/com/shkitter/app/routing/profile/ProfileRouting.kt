package com.shkitter.app.routing.profile

import com.shkitter.app.common.extensions.*
import com.shkitter.app.routing.profile.model.ProfileWithTopicsResponse
import com.shkitter.app.routing.profile.model.UpdateProfileRequest
import com.shkitter.app.routing.profile.model.UpdateProfileRequestDto
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.profile.ProfileService
import com.shkitter.domain.profile.model.UpdateProfileServiceParams
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

private const val AVATAR_MULTIPART_NAME = "avatar"

fun Routing.configureProfile() {

    val profileService by inject<ProfileService>()

    authenticate {
        route(ProfileV1.getPath()) {
            get {
                val userId = call.principalUserIdOrThrow()
                val fullProfile = profileService.getFullProfileByUserId(userId = userId)
                call.respondSuccess(ProfileWithTopicsResponse.fromDomain(data = fullProfile, baseUrl = call.baseUrl))
            }

            patch {
                val userId = call.principalUserIdOrThrow()
                val profileRequest = call.receiveOrThrow<UpdateProfileRequestDto>().validateAndConvertToVerified()
                val updatedProfile =
                    profileService.updateProfile(
                        params = getUpdateProfileParams(
                            request = profileRequest,
                            userId = userId
                        )
                    )
                call.respondSuccess(ProfileWithTopicsResponse.fromDomain(data = updatedProfile, baseUrl = call.baseUrl))
            }
        }

        post(ProfileV1.Avatar.getPath()) {
            val userId = call.principalUserIdOrThrow()
            val multipart = call.receiveMultipart()
            val filePart = multipart.readAllParts()
                .filterIsInstance<PartData.FileItem>()
                .firstOrNull { it.name == AVATAR_MULTIPART_NAME }
                ?: throw BadRequestException("Multipart must contains file item with name - $AVATAR_MULTIPART_NAME")

            val fileName = filePart.originalFileName ?: throw BadRequestException("File must have original name")

            profileService.uploadAvatar(
                userId = userId,
                origFileName = fileName,
                bytes = filePart.streamProvider().readBytes()
            )

            call.respondSuccessEmpty()
        }
    }
}

private fun getUpdateProfileParams(request: UpdateProfileRequest, userId: UUID) = UpdateProfileServiceParams(
    name = request.name,
    aboutMyself = request.aboutMyself,
    topicIds = request.topicIds,
    userId = userId
)