package org.michaelbel.movies.ui.appicon

import androidx.annotation.DrawableRes
import org.michaelbel.movies.ui_kmp.R

actual sealed class IconAlias(
    val key: String,
    @DrawableRes val iconRes: Int
) {

    data object Red: IconAlias("RedIcon", R.drawable.ic_launcher_icon_red)

    data object Purple: IconAlias("PurpleIcon", R.drawable.ic_launcher_icon_purple)

    data object Brown: IconAlias("BrownIcon", R.drawable.ic_launcher_icon_brown)

    data object Amoled: IconAlias("AmoledIcon", R.drawable.ic_launcher_icon_amoled)

    companion object {
        val VALUES = listOf(
            Red,
            Purple,
            Brown,
            Amoled
        )
    }
}