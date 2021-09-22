package com.shkitter.app.common.extensions

import io.ktor.application.*

fun Application.getConfigProperty(key: String): String = environment.config.property(key).getString()

fun Application.getConfigPropertyOrNull(key: String): String? = environment.config.propertyOrNull(key)?.getString()