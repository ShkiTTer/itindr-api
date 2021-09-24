package com.shkitter.data.db.topic

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.domain.topic.TopicDataSource
import org.jetbrains.exposed.sql.Database
import java.util.*

class TopicDataSourceImpl(private val db: Database) : TopicDataSource, DatabaseDataSource {

    override suspend fun checkTopicsExist(topicIds: List<UUID>): Boolean = db.dbQuery {
        !TopicEntity.forIds(topicIds).empty()
    }
}