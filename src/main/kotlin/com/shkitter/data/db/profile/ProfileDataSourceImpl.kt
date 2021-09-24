package com.shkitter.data.db.profile

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.profile.model.ProfileEntity
import com.shkitter.data.db.profile.model.ProfileTable
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.profile.model.ProfileWithTopics
import org.jetbrains.exposed.sql.Database
import java.util.*

class ProfileDataSourceImpl(
    private val db: Database
) : ProfileDataSource, DatabaseDataSource {

    override suspend fun getFullProfileByUserId(userId: UUID): ProfileWithTopics? = db.dbQuery {
        ProfileEntity.find { ProfileTable.userId eq userId }.firstOrNull()
    }?.toDomainWithTopics()
}