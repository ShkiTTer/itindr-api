package com.shkitter.app

import com.shkitter.app.plugins.*
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

@KtorExperimentalLocationsAPI
fun Application.module() {
    configureKoin()
    configureFlyway()
    configureStatic()
    configureSwagger()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureStatusPages()
}
