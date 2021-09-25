package com.shkitter.domain.user.model

import java.util.*

data class Like(
    val from: UUID,
    val to: UUID,
    val type: FavoriteEventType
)
