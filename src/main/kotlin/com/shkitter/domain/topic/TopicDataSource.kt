package com.shkitter.domain.topic

import com.shkitter.domain.topic.model.Topic
import java.util.*

interface TopicDataSource {
    suspend fun checkTopicsExist(topicIds: List<UUID>): Boolean
    suspend fun getAll(): List<Topic>
}