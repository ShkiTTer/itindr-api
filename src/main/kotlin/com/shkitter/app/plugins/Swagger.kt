package com.shkitter.app.plugins

import com.papsign.ktor.openapigen.OpenAPIGen
import com.shkitter.app.common.extensions.getConfigProperty
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

private const val KEY_ROOT_PATH = "ktor.deployment.rootPath"

fun Application.configureSwagger() {
    val rootPath = getConfigProperty(KEY_ROOT_PATH)

    install(OpenAPIGen) {
        serveSwaggerUi = true
        swaggerUiPath = "${rootPath}docs"
    }
}