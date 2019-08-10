package org.michaelbel.moviemade.core

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

object ViewUtil {

    fun getIcon(context: Context, @DrawableRes resource: Int, @ColorRes color: Int): Drawable {
        val iconDrawable = ContextCompat.getDrawable(context, resource) ?: context.getDrawable(resource)
        val colorDrawable = ContextCompat.getColor(context, color)

        iconDrawable?.clearColorFilter()
        iconDrawable?.mutate()
        if (Build.VERSION.SDK_INT >= 29) {
            iconDrawable?.colorFilter = BlendModeColorFilter(colorDrawable, BlendMode.COLOR)
        } else {
            iconDrawable?.setColorFilter(colorDrawable, PorterDuff.Mode.MULTIPLY)
        }

        return iconDrawable as Drawable
    }
}