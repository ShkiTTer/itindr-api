package com.shkitter.data.db.likes

import com.shkitter.data.db.user.UserTable
import com.shkitter.domain.user.model.FavoriteEventType
import com.shkitter.domain.user.model.Like
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

object LikesTable : UUIDTable("likes") {
    val from = reference(name = "from", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
    val to = reference(name = "to", foreign = UserTable, onDelete = ReferenceOption.CASCADE)
    val type = enumerationByName(name = "type", klass = FavoriteEventType::class, length = 10)
}

class LikeEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<LikeEntity>(LikesTable)

    var from by LikesTable.from
    var to by LikesTable.to
    var type by LikesTable.type

    fun toDomain() = Like(
        from = from.value,
        to = to.value,
        type = type
    )
}

