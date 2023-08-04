package org.michaelbel.movies.common.version

data class AppVersionData(
    val version: String,
    val code: Long,
    val isDebug: Boolean
) {
    companion object {
        val None: AppVersionData = AppVersionData(
            version = "",
            code = 0L,
            isDebug = false
        )
    }
}