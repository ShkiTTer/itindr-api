package com.shkitter.app

import com.shkitter.app.plugins.*
import io.ktor.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureKoin()
    configureFlyway()
    configureStatic()
    configureSwagger()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureStatusPages()
    configureRouting()
}
