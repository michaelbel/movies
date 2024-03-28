package org.michaelbel.movies.common.ktx

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_TIME_FORMAT = "yyyyMMdd_HHmmss"

val currentDateTime: String
    get() {
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        return simpleDateFormat.format(Date())
    }

fun isTimePasses(interval: Long, expireTime: Long, currentTime: Long): Boolean {
    return expireTime == 0L || currentTime.minus(expireTime) >= interval
}