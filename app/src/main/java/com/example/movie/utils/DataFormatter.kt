package com.example.movie.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.Period
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


private val DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy")
fun ZonedDateTime.toStandarDateFormat(): String = this.format(DATE_FORMATTER)
fun ZonedDateTime.toAge(): String =
    Period.between(this.toLocalDate(), LocalDate.now()).years.toString()