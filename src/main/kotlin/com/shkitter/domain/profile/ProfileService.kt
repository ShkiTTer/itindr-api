package com.shkitter.domain.profile

import com.shkitter.domain.profile.model.UpdateProfileServiceParams
import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

interface ProfileService {
    suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics
    suspend fun updateProfile(params: UpdateProfileServiceParams): ProfileWithTopics
    suspend fun uploadAvatar(userId: UUID, origFileName: String, bytes: ByteArray)
    suspend fun removeAvatar(userId: UUID)
}