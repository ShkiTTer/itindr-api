package com.shkitter.data.db.profile

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.profile.model.ProfileEntity
import com.shkitter.data.db.profile.model.ProfileTable
import com.shkitter.data.db.topic.TopicEntity
import com.shkitter.data.db.user.UserTable
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.profile.model.CreateProfileDataSourceParams
import com.shkitter.domain.profile.model.ProfileWithTopics
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SizedCollection
import java.util.*

class ProfileDataSourceImpl(
    private val db: Database
) : ProfileDataSource, DatabaseDataSource {

    override suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics? = db.dbQuery {
        ProfileEntity.find { ProfileTable.userId eq userId }.firstOrNull()
    }?.toDomainWithTopics()

    override suspend fun createProfile(params: CreateProfileDataSourceParams): ProfileWithTopics = db.dbQuery {
        ProfileEntity.new {
            userName = params.name
            aboutMyself = params.aboutMyself
            topics = SizedCollection(params.topicIds.map { TopicEntity[it] })
            userId = EntityID(params.userId, UserTable)
        }
    }.toDomainWithTopics()

    override suspend fun updateAvatar(profileId: UUID, avatar: String?): ProfileWithTopics = db.dbQuery {
        ProfileEntity[profileId].apply {
            this.avatar = avatar
        }
    }.toDomainWithTopics()
}