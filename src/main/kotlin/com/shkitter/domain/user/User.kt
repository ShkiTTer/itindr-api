package com.shkitter.domain.user

import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val password: String,
    val salt: ByteArray
)
