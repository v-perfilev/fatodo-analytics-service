package com.persoff68.fatodo.service.util

import java.time.Instant
import java.time.temporal.ChronoUnit

object InstantUtils {
    fun getPastInstant(seconds: Long): Instant {
        return Instant.now().minus(seconds, ChronoUnit.SECONDS)
    }
}
