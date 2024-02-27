package org.michaelbel.movies.ui.color.utils

import java.util.Locale

internal object StringUtils {
    fun hexFromArgb(argb: Int): String {
        val red = ColorUtils.redFromArgb(argb)
        val blue = ColorUtils.blueFromArgb(argb)
        val green = ColorUtils.greenFromArgb(argb)
        return String.format(Locale.getDefault(), "#%02x%02x%02x", red, green, blue)
    }
}