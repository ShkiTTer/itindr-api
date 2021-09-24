package com.shkitter.domain.profile

import com.shkitter.domain.profile.model.CreateProfileServiceParams
import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

interface ProfileService {
    suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics
    suspend fun createProfile(params: CreateProfileServiceParams): ProfileWithTopics
}