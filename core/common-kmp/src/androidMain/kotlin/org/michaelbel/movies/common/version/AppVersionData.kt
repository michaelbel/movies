@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.common.version

actual data class AppVersionData(
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