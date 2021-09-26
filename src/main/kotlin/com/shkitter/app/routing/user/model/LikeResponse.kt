package com.shkitter.app.routing.user.model

import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    val isMutual: Boolean
)
