package com.shkitter.app.routing.user

import com.shkitter.app.common.extensions.*
import com.shkitter.app.routing.profile.model.ProfileWithTopicsResponse
import com.shkitter.app.routing.user.model.LikeResponse
import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.common.extensions.toUUID
import com.shkitter.domain.user.UserService
import com.shkitter.domain.validation.LimitValidationRule
import com.shkitter.domain.validation.OffsetValidationRule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Routing.configureUserRouting() {

    val userService by inject<UserService>()

    authenticate {
        post(UserV1WithId.Like.getPath()) {
            val fromUserId = call.principalUserIdOrThrow()
            val toUserId = call.getPathParameter<String>(UserV1WithId.paramName).toUUID()
                ?: throw BadRequestException("Invalid user id")
            val likeResult = userService.likeUser(from = fromUserId, to = toUserId)
            call.respondSuccess(LikeResponse(isMutual = likeResult))
        }

        post(UserV1WithId.Dislike.getPath()) {
            val fromUserId = call.principalUserIdOrThrow()
            val toUserId = call.getPathParameter<String>(UserV1WithId.paramName).toUUID()
                ?: throw BadRequestException("Invalid user id")
            userService.dislikeUser(from = fromUserId, to = toUserId)
            call.respondSuccessEmpty()
        }

        get(UserV1.Feed.getPath()) {
            val userId = call.principalUserIdOrThrow()
            val usersFeed = userService.getUsersFeed(userId = userId)
            call.respondSuccess(usersFeed.map { ProfileWithTopicsResponse.fromDomain(data = it, scheme = call.scheme) })
        }

        get(UserV1.getPath()) {
            val limit = call.getQueryParameter<Int>(UserV1.limitParameterName)
                .also { LimitValidationRule.validate(it).throwBadRequestIfError() }
            val offset = call.getQueryParameter<Int>(UserV1.offsetParameterName)
                .also { OffsetValidationRule.validate(it).throwBadRequestIfError() }

            val users = userService.getAllUsers(limit = limit, offset = offset)
            call.respondSuccess(users.map { ProfileWithTopicsResponse.fromDomain(data = it, scheme = call.scheme) })
        }
    }
}