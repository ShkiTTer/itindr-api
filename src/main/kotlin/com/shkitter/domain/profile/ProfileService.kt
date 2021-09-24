package com.shkitter.domain.profile

import com.shkitter.domain.profile.model.Profile
import java.util.*

interface ProfileService {
    suspend fun getProfileByUserId(userId: UUID): Profile
}