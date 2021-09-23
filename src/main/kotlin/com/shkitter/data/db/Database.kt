package com.shkitter.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object Database {
    fun getHikariConfig(jdbcUrl: String, dbUser: String, dbPassword: String): HikariConfig = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        this.username = dbUser
        this.password = dbPassword
        driverClassName = "org.postgresql.Driver"
        isAutoCommit = true
        maximumPoolSize = 10
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    fun getHikariDataSource(config: HikariConfig): HikariDataSource = HikariDataSource(config)

    fun getDatabase(dataSource: HikariDataSource) = Database.connect(dataSource)
}