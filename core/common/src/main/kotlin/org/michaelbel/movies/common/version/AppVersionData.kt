package org.michaelbel.movies.common.version

data class AppVersionData(
    val version: String,
    val code: Long,
    val flavor: String,
    val isDebug: Boolean
) {
    companion object {
        val Empty = AppVersionData(
            version = "",
            code = 0L,
            flavor = "",
            isDebug = false
        )
    }
}