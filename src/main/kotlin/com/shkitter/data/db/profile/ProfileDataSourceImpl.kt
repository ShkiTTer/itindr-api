package com.shkitter.data.db.profile

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.profile.model.ProfileEntity
import com.shkitter.data.db.profile.model.ProfileTable
import com.shkitter.data.db.topic.TopicEntity
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.profile.model.ProfileWithTopics
import com.shkitter.domain.profile.model.UpdateProfileDataSourceParams
import org.jetbrains.exposed.sql.Database
import java.util.*

class ProfileDataSourceImpl(
    private val db: Database
) : ProfileDataSource, DatabaseDataSource {

    override suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics? = db.dbQuery {
        ProfileEntity.find { ProfileTable.userId eq userId }.firstOrNull()?.toDomainWithTopics()
    }

    override suspend fun getProfileIdByUserId(userId: UUID): UUID? = db.dbQuery {
        ProfileEntity.find { ProfileTable.userId eq userId }.firstOrNull()?.id?.value
    }

    override suspend fun createEmptyProfile(userId: UUID) = db.dbQuery {
        ProfileEntity.new {
            userName = ""
            this.userId = UserEntity[userId].id
        }
        Unit
    }

    override suspend fun updateAvatar(profileId: UUID, avatar: String?) = db.dbQuery {
        ProfileEntity[profileId].avatar = avatar
    }

    override suspend fun updateProfile(profileId: UUID, params: UpdateProfileDataSourceParams): ProfileWithTopics =
        db.dbQuery {
            ProfileEntity[profileId].apply {
                userName = params.name
                aboutMyself = params.aboutMyself
                userId = UserEntity[params.userId].id
                topics = TopicEntity.forIds(params.topicIds)
            }.toDomainWithTopics()
        }
}