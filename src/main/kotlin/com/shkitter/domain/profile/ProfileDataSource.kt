package com.shkitter.domain.profile

import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

interface ProfileDataSource {
    suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics?
}