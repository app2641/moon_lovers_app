package com.app2641.moonlovers.utils

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class DateUtils {
    companion object {
        fun toZoneDateTime(date: String): ZonedDateTime {
            return ZonedDateTime.parse(date)
        }

        fun toString(date: ZonedDateTime): String {
            return date.format(DateTimeFormatter.ISO_DATE_TIME)
        }

        fun dateDiff(from: ZonedDateTime, to: ZonedDateTime): Long {
            return ChronoUnit.MINUTES.between(from, to)
        }

        fun now(): ZonedDateTime {
            return ZonedDateTime.now(ZoneId.of("Asia/Tokyo"))
        }
    }
}