package com.shkitter.data.db.token

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.token.RefreshToken
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

    override suspend fun clearAllForUser(userId: UUID) = db.dbQuery {
        RefreshTokenEntity.find { RefreshTokenTable.userId eq userId }.forEach { it.delete() }
    }

    override suspend fun findTokenByValue(refreshToken: String): RefreshToken = db.dbQuery {
        RefreshTokenEntity.find { RefreshTokenTable.token like refreshToken }.firstOrNull()
            ?: throw NotFoundException("User is not found")
    }.toDomain()

    override suspend fun removeToken(refreshTokenId: UUID) = db.dbQuery {
        RefreshTokenEntity.findById(refreshTokenId)?.delete() ?: throw NotFoundException("Token is not found")
    }

    private suspend fun getUserById(userId: UUID): UserEntity = db.dbQuery {
        UserEntity.findById(userId)
    } ?: throw NotFoundException("User with id - '${userId}' not found")
}