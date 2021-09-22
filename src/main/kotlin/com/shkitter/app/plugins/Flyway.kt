package com.shkitter.app.plugins

import com.viartemev.ktor.flyway.FlywayFeature
import com.zaxxer.hikari.HikariDataSource
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureFlyway() {
    val hikariDataSource by inject<HikariDataSource>()

    install(FlywayFeature) {
        dataSource = hikariDataSource
    }
}