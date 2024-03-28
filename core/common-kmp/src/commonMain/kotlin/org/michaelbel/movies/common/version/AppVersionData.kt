package org.michaelbel.movies.common.version

data class AppVersionData(
    val flavor: String
) {
    companion object {
        val Empty = AppVersionData(
            flavor = ""
        )
    }
}