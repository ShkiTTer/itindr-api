package com.shkitter.domain.user

interface UserDataSource {
    suspend fun getUserByEmail(email: String): User?
}