package org.michaelbel.movies.common

import org.michaelbel.movies.common.theme.AppTheme

data class ThemeData(
    val appTheme: AppTheme,
    val dynamicColors: Boolean,
    val paletteKey: Int,
    val seedColor: Int
) {
    companion object {
        const val STYLE_TONAL_SPOT = 0
        const val STYLE_SPRITZ = 1
        const val STYLE_FRUIT_SALAD = 2
        const val STYLE_VIBRANT = 3
        const val STYLE_MONOCHROME = 4

        const val DEFAULT_SEED_COLOR = -12687058 // First color from Palette List
        const val DEFAULT_SEED_COLOR_HEX = 0xFF3E692E // String.format("#%06X", (0xFFFFFF and DEFAULT_SEED_COLOR))

        val Default: ThemeData
            get() = ThemeData(
                appTheme = AppTheme.FollowSystem,
                dynamicColors = false,
                paletteKey = STYLE_TONAL_SPOT,
                seedColor = DEFAULT_SEED_COLOR
            )
    }
}