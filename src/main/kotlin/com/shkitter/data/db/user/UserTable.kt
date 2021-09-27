package com.shkitter.data.db.user

import com.shkitter.data.db.profile.model.ProfileEntity
import com.shkitter.data.db.profile.model.ProfileTable
import com.shkitter.domain.user.model.User
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

object UserTable : UUIDTable(name = "users") {
    val email = varchar(name = "email", length = 255)
    val password = varchar(name = "password", length = 255)
    val salt = binary("salt", 16)
}

class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UserTable)

    var email by UserTable.email
    var password by UserTable.password
    var salt by UserTable.salt

    val profile by ProfileEntity referencedOn ProfileTable.userId

    fun toDomain() = User(
        id = id.value,
        email = email,
        password = password,
        salt = salt
    )
}