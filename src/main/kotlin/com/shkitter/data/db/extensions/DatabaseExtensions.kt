package com.shkitter.data.db.extensions

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> Database.dbQuery(block: () -> T): T = newSuspendedTransaction(Dispatchers.IO, this) { block.invoke() }