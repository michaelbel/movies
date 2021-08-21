@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context
import android.graphics.Color
import androidx.annotation.*
import androidx.annotation.IntRange
import timber.log.Timber
import kotlin.math.floor
import kotlin.math.roundToInt

inline fun Context.getAttrColor(@AttrRes colorAttr: Int): Int {
    var color = 0
    val attrs = intArrayOf(colorAttr)

    try {
        val typedArray = obtainStyledAttributes(attrs)
        color = typedArray.getColor(0, 0)
        typedArray.recycle()
    } catch (e: Exception) {
        Timber.e(e)
    }

    return color
}

inline fun Context.getColorArray(@ArrayRes arrayRes: Int): IntArray? {
    if (arrayRes == 0) {
        return null
    }

    val ta = resources.obtainTypedArray(arrayRes)
    val colors = IntArray(ta.length())

    for (i in 0 until ta.length()) {
        colors[i] = ta.getColor(i, 0)
    }

    ta.recycle()
    return colors
}

@ColorInt
inline fun adjustAlpha(@ColorInt color: Int, @FloatRange(from = 0.00, to = 1.00) factor: Float): Int {
    val alpha = (Color.alpha(color) * factor).roundToInt()
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return Color.argb(alpha, red, green, blue)
}

@ColorInt
inline fun modifyAlpha(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int): Int = color and 0x00FFFFFF or (alpha shl 24)

inline fun rgbToHsv(r: Int, g: Int, b: Int): DoubleArray {
    val rf = r / 255.0
    val gf = g / 255.0
    val bf = b / 255.0
    val max = if (rf > gf && rf > bf) rf else if (gf > bf) gf else bf
    val min = if (rf < gf && rf < bf) rf else if (gf < bf) gf else bf
    var h: Double
    val s: Double
    val d = max - min

    s = if (max == 0.0) 0.0 else d / max

    if (max == min) {
        h = 0.0
    } else {
        h = if (rf > gf && rf > bf) {
            (gf - bf) / d + if (gf < bf) 6 else 0
        } else if (gf > bf) {
            (bf - rf) / d + 2
        } else {
            (rf - gf) / d + 4
        }

        h /= 6.0
    }

    return doubleArrayOf(h, s, max)
}

inline fun hsvToRgb(h: Double, s: Double, v: Double): IntArray {
    var r = 0.0
    var g = 0.0
    var b = 0.0
    val i = floor(h * 6).toInt().toDouble()
    val f = h * 6 - i
    val p = v * (1 - s)
    val q = v * (1 - f * s)
    val t = v * (1 - (1 - f) * s)

    when (i.toInt() % 6) {
        0 -> {
            r = v
            g = t
            b = p
        }
        1 -> {
            r = q
            g = v
            b = p
        }
        2 -> {
            r = p
            g = v
            b = t
        }
        3 -> {
            r = p
            g = q
            b = v
        }
        4 -> {
            r = t
            g = p
            b = v
        }
        5 -> {
            r = v
            g = p
            b = q
        }
    }

    return intArrayOf((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
}