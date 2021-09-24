package com.shkitter.data.db.user

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.domain.user.User
import com.shkitter.domain.user.UserDataSource
import org.jetbrains.exposed.sql.Database
import java.util.*

class UserDataSourceImpl(private val db: Database) : UserDataSource, DatabaseDataSource {

    override suspend fun getUserByEmail(email: String): User? = db.dbQuery {
        UserEntity.find { UserTable.email like email }.firstOrNull()
    }?.toDomain()

    override suspend fun createNewUser(email: String, password: String, salt: ByteArray) = db.dbQuery {
        UserEntity.new {
            this.email = email
            this.password = password
            this.salt = salt
        }
    }.toDomain()

    override suspend fun getUserById(userId: UUID): User? = db.dbQuery {
        UserEntity.findById(userId)
    }?.toDomain()
}