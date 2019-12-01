@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Transformation

fun ImageView.setTint(@ColorRes color: Int) {
    setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
}

inline fun ImageView.loadImage(url: String,
                               resize: Pair<Int, Int> = Pair(0, 0),
                               resizeDimen: Pair<Int, Int> = Pair(0, 0),
                               @DrawableRes placeholder: Int = 0,
                               @DrawableRes error: Int = 0,
                               transformation: Transformation? = null) {
    val picasso: RequestCreator = Picasso.get().load(url)

    if (resize != Pair(0, 0)) {
        picasso.resize(resize.first, resize.second)
    }

    if (resizeDimen != Pair(0, 0)) {
        picasso.resizeDimen(resizeDimen.first, resizeDimen.second)
    }

    if (placeholder != 0) {
        picasso.placeholder(placeholder)
    }

    if (error != 0) {
        picasso.error(error)
    }

    if (transformation != null) {
        picasso.transform(transformation)
    }

    picasso.into(this)
}

inline fun ImageView.setIcon(@DrawableRes resource: Int, @ColorRes color: Int) {
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