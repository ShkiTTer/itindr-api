package com.shkitter.domain.user

import com.shkitter.domain.profile.model.ProfileWithTopics
import java.util.*

interface UserService {

    suspend fun getAllUsers(limit: Int, offset: Int): List<ProfileWithTopics>
    suspend fun getUsersFeed(userId: UUID): List<ProfileWithTopics>

    suspend fun likeUser(from: UUID, to: UUID): Boolean
    suspend fun dislikeUser(from: UUID, to: UUID)
}