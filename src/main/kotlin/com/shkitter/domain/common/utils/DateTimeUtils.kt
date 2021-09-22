package com.shkitter.domain.common.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

object DateTimeUtils {
    private const val UTC = "UTC"

    fun getCurrentSeconds() = ZonedDateTime.now(ZoneId.of(UTC)).toEpochSecond()

    fun fromSeconds(seconds: Long): ZonedDateTime =
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.of(UTC))
}