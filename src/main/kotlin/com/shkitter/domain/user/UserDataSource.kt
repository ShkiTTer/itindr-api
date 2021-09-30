package com.shkitter.domain.user

import com.shkitter.domain.user.model.User
import java.util.*

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
    suspend fun createNewUser(email: String, password: String, salt: ByteArray): User
    suspend fun getUserById(userId: UUID): User?

    suspend fun likeUser(from: UUID, to: UUID): Boolean
    suspend fun dislikeUser(from: UUID, to: UUID)
    suspend fun hasReaction(from: UUID, to: UUID): Boolean
    suspend fun hasMutualLike(firstUserId: UUID, secondUserId: UUID): Boolean
}