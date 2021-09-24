package com.shkitter.data.db.profile.model

import com.shkitter.data.db.topic.TopicTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object ProfileTopicTable : Table(name = "profile_topic") {
    val profileId = reference(name = "profile_id", foreign = ProfileTable, onDelete = ReferenceOption.CASCADE)
    val topicId = reference(name = "topic_id", foreign = TopicTable, onDelete = ReferenceOption.CASCADE)

    override val primaryKey: PrimaryKey = PrimaryKey(profileId, topicId)
}