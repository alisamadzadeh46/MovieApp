package com.example.movie.model.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

class ZoneDateTimeAdapter {

    @FromJson
    fun fromJson(json: String?): ZonedDateTime? {
        if (json.isNullOrEmpty()) {
            return null
        }
        val localDate = LocalDate.parse(json, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return localDate.atStartOfDay(ZoneId.systemDefault())
    }

    @ToJson
    fun toJson(value:ZonedDateTime?):String?{
        return value?.toString()
    }
}