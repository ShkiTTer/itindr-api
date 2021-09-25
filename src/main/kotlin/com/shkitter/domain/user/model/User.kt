package com.shkitter.domain.user.model

import java.util.*

data class User(
    val id: UUID,
    val email: String,
    val password: String,
    val salt: ByteArray
)
