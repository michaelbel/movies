package org.michaelbel.movies.ui.appicon

import org.jetbrains.compose.resources.DrawableResource
import org.michaelbel.movies.ui.icons.MoviesIcons

sealed class IconAlias(
    val key: String,
    val iconRes: DrawableResource
) {

    data object Red: IconAlias(
        "RedIcon",
        MoviesIcons.LauncherRed
    )

    data object Purple: IconAlias(
        "PurpleIcon",
        MoviesIcons.LauncherPurple
    )

    data object Brown: IconAlias(
        "BrownIcon",
        MoviesIcons.LauncherBrown
    )

    data object Amoled: IconAlias(
        "AmoledIcon",
        MoviesIcons.LauncherAmoled
    )

    companion object {
        val VALUES = listOf(
            Red,
            Purple,
            Brown,
            Amoled
        )
    }
}