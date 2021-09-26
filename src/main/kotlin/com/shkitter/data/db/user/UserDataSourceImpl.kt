package com.shkitter.data.db.user

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.likes.LikeEntity
import com.shkitter.data.db.likes.LikesTable
import com.shkitter.domain.user.UserDataSource
import com.shkitter.domain.user.model.FavoriteEventType
import com.shkitter.domain.user.model.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
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

    override suspend fun likeUser(from: UUID, to: UUID): Boolean = db.dbQuery {
        LikeEntity.new {
            this.from = UserEntity[from].id
            this.to = UserEntity[to].id
            type = FavoriteEventType.LIKE
        }

        LikeEntity.find {
            (LikesTable.to eq from) and (LikesTable.from eq to) and (LikesTable.type eq FavoriteEventType.LIKE)
        }.firstOrNull() != null
    }

    override suspend fun dislikeUser(from: UUID, to: UUID) = db.dbQuery {
        LikeEntity.new {
            this.from = UserEntity[from].id
            this.to = UserEntity[to].id
            type = FavoriteEventType.DISLIKE
        }
        Unit
    }

    override suspend fun hasReaction(from: UUID, to: UUID): Boolean = db.dbQuery {
        LikeEntity.find {
            (LikesTable.from eq from) and (LikesTable.to eq to)
        }.firstOrNull() != null
    }
}