package com.shkitter.data.db.token

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.token.TokenDataSource
import com.shkitter.domain.token.TokenInfo
import org.jetbrains.exposed.sql.Database
import java.util.*

class TokenDataSourceImpl(private val db: Database) : TokenDataSource, DatabaseDataSource {

    override suspend fun saveTokenInfo(userId: UUID, tokenInfo: TokenInfo) = db.dbQuery {
        val user = getUserById(userId)

        RefreshTokenEntity.new {
            this.token = tokenInfo.refreshToken
            this.expiredAt = tokenInfo.expiredRefresh
            this.userId = user.id
        }

        Unit
    }

    private suspend fun getUserById(userId: UUID): UserEntity = db.dbQuery {
        UserEntity.findById(userId)
    } ?: throw NotFoundException("User with id - '${userId}' not found")

    override suspend fun clearAllForUser(userId: UUID) = db.dbQuery {
        RefreshTokenEntity.find { RefreshTokenTable.userId eq userId }.forEach { it.delete() }
    }
}