package com.shkitter.data.db.user

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.domain.user.User
import com.shkitter.domain.user.UserDataSource
import org.jetbrains.exposed.sql.Database

class UserDataSourceImpl(private val db: Database) : UserDataSource, DatabaseDataSource {

    override suspend fun getUserByEmail(email: String): User? = db.dbQuery {
        UserEntity.find { UserTable.email like email }.firstOrNull()
    }?.toDomain()
}