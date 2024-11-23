package org.michaelbel.movies.ui.appicon

import org.jetbrains.compose.resources.DrawableResource
import org.michaelbel.movies.ui.icons.MoviesIcons

sealed class IconAlias(
    val key: String,
    val iconRes: DrawableResource
) {

    data object Red: IconAlias(
        key = "RedIcon",
        iconRes = MoviesIcons.LauncherRed
    )

    data object Purple: IconAlias(
        key = "PurpleIcon",
        iconRes = MoviesIcons.LauncherPurple
    )

    data object Brown: IconAlias(
        key = "BrownIcon",
        iconRes = MoviesIcons.LauncherBrown
    )

    data object Amoled: IconAlias(
        key = "AmoledIcon",
        iconRes = MoviesIcons.LauncherAmoled
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