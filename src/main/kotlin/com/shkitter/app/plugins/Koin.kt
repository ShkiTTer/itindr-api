package com.shkitter.app.plugins

import com.shkitter.app.di.KoinModules
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(KoinModules.all)
    }
}