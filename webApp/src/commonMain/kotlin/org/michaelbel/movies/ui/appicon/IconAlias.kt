package org.michaelbel.movies.ui.appicon

sealed class IconAlias(
    val key: String,
    val title: String
) {

    data object Red: IconAlias(
        key = "RedIcon",
        title = "Red"
    )

    companion object {
        val VALUES = listOf(Red)
    }
}
