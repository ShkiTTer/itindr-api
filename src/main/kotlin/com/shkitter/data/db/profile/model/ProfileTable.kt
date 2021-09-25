package com.shkitter.data.db.profile.model

import com.shkitter.data.db.topic.TopicEntity
import com.shkitter.data.db.user.UserTable
import com.shkitter.domain.profile.model.Profile
import com.shkitter.domain.profile.model.ProfileWithTopics
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

object ProfileTable : UUIDTable(name = "profile") {
    val userName = varchar(name = "user_name", length = 100)
    val aboutMyself = varchar(name = "about_myself", length = 250).nullable().default(null)
    val avatar = varchar(name = "avatar", length = 255).nullable().default(null)

    val userId = reference(name = "user_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
}

class ProfileEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ProfileEntity>(ProfileTable)

    var userName by ProfileTable.userName
    var aboutMyself by ProfileTable.aboutMyself
    var avatar by ProfileTable.avatar

    var userId by ProfileTable.userId
    var topics by TopicEntity via ProfileTopicTable

    fun toDomain() = Profile(
        id = id.value,
        name = userName,
        aboutMyself = aboutMyself,
        avatar = avatar,
        userId = userId.value
    )

    fun toDomainWithTopics() = ProfileWithTopics(
        id = id.value,
        name = userName,
        aboutMyself = aboutMyself,
        avatar = avatar,
        userId = userId.value,
        topics = topics.map { it.toDomain() }
    )
}