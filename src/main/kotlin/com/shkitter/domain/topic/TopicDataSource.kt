package com.shkitter.domain.topic

import java.util.*

interface TopicDataSource {
    suspend fun checkTopicsExist(topicIds: List<UUID>): Boolean
}