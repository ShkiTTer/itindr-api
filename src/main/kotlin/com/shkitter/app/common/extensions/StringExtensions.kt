package com.shkitter.app.common.extensions

import java.util.*

fun String.toUUID() = try {
    UUID.fromString(this)
} catch (e: Exception) {
    null
}