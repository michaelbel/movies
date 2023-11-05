package org.michaelbel.movies.common.ktx

fun isTimePasses(interval: Long, expireTime: Long, currentTime: Long): Boolean {
    return expireTime == 0L || currentTime.minus(expireTime) >= interval
}