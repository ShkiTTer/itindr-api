package com.shkitter.data.db.topic

import com.shkitter.domain.topic.model.Topic
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object TopicTable : UUIDTable(name = "topic") {
    val topicTitle = varchar(name = "topic_title", length = 100)
}

class TopicEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TopicEntity>(TopicTable)

    var topicTitle by TopicTable.topicTitle

    fun toDomain() = Topic(
        id = id.value,
        name = topicTitle
    )
}