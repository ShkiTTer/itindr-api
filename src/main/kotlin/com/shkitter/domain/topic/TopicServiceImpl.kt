package com.shkitter.domain.topic

import com.shkitter.domain.topic.model.Topic

class TopicServiceImpl(
    private val topicDataSource: TopicDataSource
) : TopicService {

    override suspend fun getAll(): List<Topic> = topicDataSource.getAll()
}