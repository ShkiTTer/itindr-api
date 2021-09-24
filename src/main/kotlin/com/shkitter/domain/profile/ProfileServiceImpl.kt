package com.shkitter.domain.profile

import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.profile.model.Profile
import com.shkitter.domain.user.UserDataSource
import java.util.*

class ProfileServiceImpl(
    private val profileDataSource: ProfileDataSource,
    private val userDataSource: UserDataSource
) : ProfileService {

    override suspend fun getProfileByUserId(userId: UUID): Profile {
        userDataSource.getUserById(userId) ?: throw NotFoundException("User with id - '$userId' not found")
        return profileDataSource.getFullProfileByUserId(userId)
            ?: throw NotFoundException("Profile for user with id - '$userId' not found")
    }
}