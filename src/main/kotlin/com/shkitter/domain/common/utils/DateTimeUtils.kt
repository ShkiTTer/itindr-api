package com.shkitter.domain.common.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeUtils {
    private const val UTC = "UTC"

    fun getCurrentSeconds() = getNow().toEpochSecond()

    fun fromSeconds(seconds: Long): ZonedDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of(UTC))

    fun instantFromSeconds(seconds: Long): Instant = Instant.ofEpochSecond(seconds)

    fun getNow() = ZonedDateTime.now(ZoneId.of(UTC))
}