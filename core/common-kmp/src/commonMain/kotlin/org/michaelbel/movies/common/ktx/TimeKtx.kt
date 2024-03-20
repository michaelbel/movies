@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.common.ktx

expect val currentDateTime: String

expect fun isTimePasses(
    interval: Long,
    expireTime: Long,
    currentTime: Long
): Boolean