package com.shkitter.domain.user

import java.util.*

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
    suspend fun createNewUser(email: String, password: String, salt: ByteArray): User
    suspend fun getUserById(userId: UUID): User?
}