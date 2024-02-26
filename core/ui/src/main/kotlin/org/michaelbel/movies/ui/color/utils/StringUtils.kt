package org.michaelbel.movies.ui.color.utils

internal object StringUtils {
    fun hexFromArgb(argb: Int): String {
        val red = ColorUtils.redFromArgb(argb)
        val blue = ColorUtils.blueFromArgb(argb)
        val green = ColorUtils.greenFromArgb(argb)
        return String.format("#%02x%02x%02x", red, green, blue)
    }
}