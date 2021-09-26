package com.shkitter.data.db.profile

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.likes.LikeEntity
import com.shkitter.data.db.likes.LikesTable
import com.shkitter.data.db.profile.model.ProfileEntity
import com.shkitter.data.db.profile.model.ProfileTable
import com.shkitter.data.db.topic.TopicEntity
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.profile.model.ProfileWithTopics
import com.shkitter.domain.profile.model.UpdateProfileDataSourceParams
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.or
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

    override suspend fun getUserFeed(userId: UUID): List<ProfileWithTopics> = db.dbQuery {
        val currentUserProfile = ProfileEntity.find { ProfileTable.userId eq userId }.first()
        val contactUsers = LikeEntity
            .find {
                ((LikesTable.from eq userId) and (LikesTable.to eq currentUserProfile.userId)) or
                    ((LikesTable.from eq currentUserProfile.userId) and (LikesTable.to eq userId))
            }
            .map { if (it.from.value != userId) it.from else it.to }
            .distinct()

        val notContactUsers = ProfileEntity.find {
            (ProfileTable.userId neq userId) and (ProfileTable.userId notInList contactUsers)
        }

        notContactUsers
            .map { profile -> profile to profile.topics.intersect(currentUserProfile.topics).size }
            .sortedByDescending { it.second }
            .map { it.first.toDomainWithTopics() }
    }
}