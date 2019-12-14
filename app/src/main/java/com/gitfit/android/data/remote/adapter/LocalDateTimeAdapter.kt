package com.gitfit.android.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @ToJson
    fun toJson(value: LocalDateTime): String {
        return FORMATTER.format(value.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime())
    }

    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return FORMATTER.parse(value, LocalDateTime.FROM).atZone(ZoneOffset.UTC)
            .withZoneSameInstant(
                ZoneId.systemDefault()
            ).toLocalDateTime()
    }

    companion object {
        private val FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    }
}