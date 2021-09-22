package com.shkitter.domain.user

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
    suspend fun createNewUser(email: String, password: String, salt: ByteArray): User
}