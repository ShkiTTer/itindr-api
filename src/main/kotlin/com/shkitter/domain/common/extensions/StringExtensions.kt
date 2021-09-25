package com.shkitter.domain.common.extensions

import java.util.*

private const val POINT_SEPARATOR = "."

fun String.toUUID() = try {
    UUID.fromString(this)
} catch (e: Exception) {
    null
}

fun String.getFileExtensions() = this.substring(this.lastIndexOf(POINT_SEPARATOR) + 1)