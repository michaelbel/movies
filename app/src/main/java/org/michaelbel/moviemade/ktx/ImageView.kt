@file:Suppress("unused")

package org.michaelbel.moviemade.ktx

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun ImageView.setTint(@ColorRes color: Int) {
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}

fun ImageView.setIcon(@DrawableRes resource: Int, @ColorRes color: Int) {
    val iconDrawable = ContextCompat.getDrawable(context, resource) ?: context.getDrawable(resource)
    val colorDrawable = ContextCompat.getColor(context, color)

    iconDrawable?.clearColorFilter()
    iconDrawable?.mutate()
    if (Build.VERSION.SDK_INT >= 29) {
        iconDrawable?.colorFilter = BlendModeColorFilter(colorDrawable, BlendMode.COLOR)
    } else {
        iconDrawable?.setColorFilter(colorDrawable, PorterDuff.Mode.MULTIPLY)
    }

    this.setImageDrawable(iconDrawable)
}