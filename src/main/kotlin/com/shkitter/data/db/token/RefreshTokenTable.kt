package com.shkitter.data.db.token

import com.shkitter.data.db.user.UserTable
import com.shkitter.domain.common.utils.DateTimeUtils
import com.shkitter.domain.token.RefreshToken
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

object RefreshTokenTable : UUIDTable(name = "refresh_token") {
    val token = varchar(name = "token", length = 64)
    val expiredAt = long("expired_at")
    val userId = reference(name = "user_id", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
}

class RefreshTokenEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<RefreshTokenEntity>(RefreshTokenTable)

    var token by RefreshTokenTable.token
    var expiredAt by RefreshTokenTable.expiredAt
    var userId by RefreshTokenTable.userId

    fun toDomain() = RefreshToken(
        token = token,
        expiredAt = DateTimeUtils.fromSeconds(expiredAt),
        userId = userId.value.toString()
    )
}