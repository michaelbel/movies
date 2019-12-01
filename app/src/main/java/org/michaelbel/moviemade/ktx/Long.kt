@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import java.util.*
import kotlin.math.pow

inline fun Long.formatSize(): String {
    return when {
        this < 1024 -> String.format(Locale.getDefault(), "%d B", this)
        this < 1024.0.pow(2.0) -> String.format(Locale.getDefault(), "%.1f KB", this / 1024.0F)
        this < 1024.0.pow(3.0) -> String.format(Locale.getDefault(), "%.1f MB", this / 1024.0.pow(2.0))
        this < 1024.0.pow(4.0) -> String.format(Locale.getDefault(), "%.1f GB", this / 1024.0.pow(3.0))
        this < 1024.0.pow(5.0) -> String.format(Locale.getDefault(), "%.1f TB", this / 1024.0.pow(4.0))
        this < 1024.0.pow(6.0) -> String.format(Locale.getDefault(), "%.1f PB", this / 1024.0.pow(5.0))
        this < 1024.0.pow(7.0) -> String.format(Locale.getDefault(), "%.1f EB", this / 1024.0.pow(6.0))
        this < 1024.0.pow(8.0) -> String.format(Locale.getDefault(), "%.1f ZB", this / 1024.0.pow(7.0))
        else -> String.format(Locale.getDefault(), "%.1f YB", this / 1024.0.pow(8.0))
    }
}