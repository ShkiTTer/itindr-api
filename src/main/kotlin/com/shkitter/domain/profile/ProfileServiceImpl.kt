package com.shkitter.domain.profile

import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.files.FilesDataSource
import com.shkitter.domain.profile.model.CreateProfileDataSourceParams
import com.shkitter.domain.profile.model.CreateProfileServiceParams
import com.shkitter.domain.profile.model.ProfileWithTopics
import com.shkitter.domain.topic.TopicDataSource
import com.shkitter.domain.user.UserDataSource
import java.util.*

class ProfileServiceImpl(
    private val profileDataSource: ProfileDataSource,
    private val userDataSource: UserDataSource,
    private val topicDataSource: TopicDataSource,
    private val filesDataSource: FilesDataSource
) : ProfileService {

    override suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics {
        userDataSource.getUserById(userId) ?: throw NotFoundException("User with id - '$userId' not found")
        return profileDataSource.getFullProfileByUserId(userId)
            ?: throw NotFoundException("Profile for user with id - '$userId' not found")
    }

    override suspend fun createProfile(params: CreateProfileServiceParams): ProfileWithTopics {
        if (params.topicIds.isNotEmpty() && !topicDataSource.checkTopicsExist(params.topicIds)) {
            throw NotFoundException("Some topics not found")
        }

        userDataSource.getUserById(params.userId)
            ?: throw NotFoundException("User with id - '${params.userId}' not found")

        val dataSourceParams = CreateProfileDataSourceParams(
            name = params.name,
            aboutMyself = params.aboutMyself,
            userId = params.userId,
            topicIds = params.topicIds
        )

        return profileDataSource.createProfile(dataSourceParams)
    }

    private fun createAvatarFileName(userId: UUID) = userId.toString()
}