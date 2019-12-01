@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

inline fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) {
        return bitmap
    }

    val bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    } else {
        Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    }

    Canvas(bitmap).apply {
        setBounds(0, 0, width, height)
        draw(this)
    }

    return bitmap
}

fun Context.getTintDrawable(@DrawableRes drawableRes: Int, @ColorRes colorRes: Int): Drawable {
    val source = ContextCompat.getDrawable(this, drawableRes)?.mutate()
    val wrapped = DrawableCompat.wrap(source as Drawable)
    DrawableCompat.setTint(wrapped, ContextCompat.getColor(this, colorRes))
    return wrapped
}