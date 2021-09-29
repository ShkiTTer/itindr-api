package com.shkitter.domain.profile

import com.shkitter.domain.profile.model.UpdateProfileDataSourceParams
import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

interface ProfileDataSource {
    suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics?
    suspend fun getProfileIdByUserId(userId: UUID): UUID?

    suspend fun createEmptyProfile(userId: UUID)
    suspend fun updateAvatar(profileId: UUID, avatar: String?)
    suspend fun updateProfile(profileId: UUID, params: UpdateProfileDataSourceParams): ProfileWithTopics
    suspend fun removeAvatar(userId: UUID): String?

    suspend fun getAll(limit: Int, offset: Int): List<ProfileWithTopics>
    suspend fun getUserFeed(userId: UUID): List<ProfileWithTopics>
}