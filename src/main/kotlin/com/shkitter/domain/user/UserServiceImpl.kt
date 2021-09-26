package com.shkitter.domain.user

import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.common.exceptions.ResourceAlreadyExistException
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

class UserServiceImpl(
    private val userDataSource: UserDataSource,
    private val profileDataSource: ProfileDataSource
) : UserService {

    override suspend fun getUsersFeed(userId: UUID): List<ProfileWithTopics> {
        checkUserExistOrThrow(userId)
        return profileDataSource.getUserFeed(userId)
    }

    override suspend fun likeUser(from: UUID, to: UUID): Boolean {
        checkUserExistOrThrow(from)
        checkUserExistOrThrow(to)
        checkUserReactionExistOrThrow(from, to)
        return userDataSource.likeUser(from, to)
    }

    override suspend fun dislikeUser(from: UUID, to: UUID) {
        checkUserExistOrThrow(from)
        checkUserExistOrThrow(to)
        checkUserReactionExistOrThrow(from, to)
        userDataSource.dislikeUser(from, to)
    }

    private suspend fun checkUserExistOrThrow(userId: UUID) {
        userDataSource.getUserById(userId) ?: throw NotFoundException("User with id '$userId' not found")
    }

    private suspend fun checkUserReactionExistOrThrow(from: UUID, to: UUID) {
        if (userDataSource.hasReaction(from, to)) {
            throw ResourceAlreadyExistException("There is already a reaction from the user with id - $from for the user with id - $to")
        }
    }
}