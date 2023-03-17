package org.michaelbel.movies.common.ktx

fun isTimePasses(interval: Long, expireTime: Long, currentTime: Long): Boolean {
    return (currentTime - expireTime) >= interval
}