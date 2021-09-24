package com.shkitter.domain.profile

import com.shkitter.domain.profile.model.CreateProfileDataSourceParams
import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

interface ProfileDataSource {
    suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics?
    suspend fun createProfile(params: CreateProfileDataSourceParams): ProfileWithTopics
    suspend fun updateAvatar(profileId: UUID, avatar: String?): ProfileWithTopics
}