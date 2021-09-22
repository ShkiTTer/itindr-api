package com.shkitter.data.db.auth

import com.shkitter.data.db.common.extensions.DatabaseDataSource
import com.shkitter.data.db.token.RefreshTokenEntity
import com.shkitter.data.db.user.UserEntity
import com.shkitter.domain.auth.AuthDataSource
import com.shkitter.domain.common.exceptions.NotFoundException
import com.shkitter.domain.token.TokenInfo
import org.jetbrains.exposed.sql.Database
import java.util.*

class AuthDataSourceImpl(private val db: Database) : AuthDataSource, DatabaseDataSource {

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
    } ?: throw NotFoundException("User with id - \"${userId}\" not found")
}