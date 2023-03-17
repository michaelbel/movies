package org.michaelbel.movies.common.version

data class AppVersionData(
    val version: String,
    val code: Long
) {
    companion object {
        val None: AppVersionData = AppVersionData("", 0L)
    }
}