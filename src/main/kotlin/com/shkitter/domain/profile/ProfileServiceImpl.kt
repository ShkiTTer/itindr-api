package com.shkitter.domain.profile

import com.shkitter.domain.common.exceptions.BadRequestException
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.common.extensions.getFileExtensions
import com.shkitter.domain.common.utils.FileUtils
import com.shkitter.domain.files.FilesDataSource
import com.shkitter.domain.profile.model.ProfileWithTopics
import com.shkitter.domain.profile.model.UpdateProfileDataSourceParams
import com.shkitter.domain.profile.model.UpdateProfileServiceParams
import com.shkitter.domain.topic.TopicDataSource
import com.shkitter.domain.user.UserDataSource
import java.util.*

class ProfileServiceImpl(
    private val profileDataSource: ProfileDataSource,
    private val userDataSource: UserDataSource,
    private val topicDataSource: TopicDataSource,
    private val filesDataSource: FilesDataSource
) : ProfileService {

    private companion object {
        const val PREFIX_AVATAR = "avatar_"
    }

    override suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics {
        checkUserExistOrThrow(userId)
        return profileDataSource.getFullProfileByUserId(userId)
            ?: throw NotFoundException("Profile for user with id - '$userId' not found")
    }

    override suspend fun updateProfile(params: UpdateProfileServiceParams): ProfileWithTopics {
        if (params.topicIds.isNotEmpty() && !topicDataSource.checkTopicsExist(params.topicIds)) {
            throw NotFoundException("Some topics not found")
        }

        checkUserExistOrThrow(userId = params.userId)

        val profileId = profileDataSource.getProfileIdByUserId(params.userId)
            ?: throw NotFoundException("Profile not found")

        return profileDataSource.updateProfile(profileId = profileId, params = getUpdateDataSourceParams(params))
    }

    override suspend fun uploadAvatar(userId: UUID, origFileName: String, bytes: ByteArray) {
        if (bytes.isEmpty()) throw BadRequestException("File is empty")

        val fileExtension = origFileName.getFileExtensions()
        if (!FileUtils.isExtensionAllowed(fileExtension)) {
            throw BadRequestException("File extension - $fileExtension is not allowed")
        }

        checkUserExistOrThrow(userId)

        val profileId = profileDataSource.getProfileIdByUserId(userId = userId)
            ?: throw NotFoundException("Profile not found")

        val savedAvatarPath = filesDataSource.saveFile(
            fileName = createAvatarFileName(profileId),
            extension = fileExtension,
            bytes = bytes,
            deleteOnExit = true
        )
        profileDataSource.updateAvatar(profileId = profileId, avatar = savedAvatarPath)
    }

    override suspend fun removeAvatar(userId: UUID) {
        checkUserExistOrThrow(userId)
        profileDataSource.removeAvatar(userId)?.let { path ->
            filesDataSource.removeFile(path)
        }
    }

    private suspend fun checkUserExistOrThrow(userId: UUID) {
        userDataSource.getUserById(userId) ?: throw NotFoundException("User with id - '$userId' not found")
    }

    private fun createAvatarFileName(profileId: UUID) = "$PREFIX_AVATAR$profileId"

    private fun getUpdateDataSourceParams(params: UpdateProfileServiceParams) = UpdateProfileDataSourceParams(
        name = params.name,
        aboutMyself = params.aboutMyself,
        userId = params.userId,
        topicIds = params.topicIds
    )

}