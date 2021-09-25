package com.shkitter.domain.topic

import com.shkitter.domain.topic.model.Topic

interface TopicService {
    suspend fun getAll(): List<Topic>
}