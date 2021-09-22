package com.shkitter.data.db.common.extensions

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

interface DatabaseDataSource {
    suspend fun <T> Database.dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, this) { block.invoke() }
}