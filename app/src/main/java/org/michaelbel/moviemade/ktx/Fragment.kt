@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

inline fun Fragment.getIcon(@DrawableRes resource: Int, @ColorRes color: Int): Drawable? {
    val iconDrawable = ContextCompat.getDrawable(requireContext(), resource) ?: requireContext().getDrawable(resource)
    val colorDrawable = ContextCompat.getColor(requireContext(), color)

    iconDrawable?.clearColorFilter()
    iconDrawable?.mutate()
    if (Build.VERSION.SDK_INT >= 29) {
        iconDrawable?.colorFilter = BlendModeColorFilter(colorDrawable, BlendMode.COLOR)
    } else {
        iconDrawable?.setColorFilter(colorDrawable, PorterDuff.Mode.MULTIPLY)
    }

    return iconDrawable
}