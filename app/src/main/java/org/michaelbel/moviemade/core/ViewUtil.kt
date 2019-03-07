package org.michaelbel.moviemade.core

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

object ViewUtil {

    @RequiresApi(12)
    fun clearCursorDrawable(editText: EditText) {
        val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        mCursorDrawableRes.isAccessible = true
        mCursorDrawableRes.setInt(editText, 0)
    }

    fun getIcon(context: Context, @DrawableRes resource: Int, colorFilter: Int): Drawable? =
            getIcon(context, resource, colorFilter, PorterDuff.Mode.MULTIPLY)

    private fun getIcon(context: Context, @DrawableRes resource: Int, colorFilter: Int, mode: PorterDuff.Mode): Drawable? {
        val iconDrawable = ContextCompat.getDrawable(context, resource)
        iconDrawable?.clearColorFilter()
        iconDrawable?.mutate()?.setColorFilter(colorFilter, mode)
        return iconDrawable
    }
}