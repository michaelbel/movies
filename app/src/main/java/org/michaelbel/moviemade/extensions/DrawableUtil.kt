package org.michaelbel.moviemade.extensions

import android.annotation.TargetApi
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

object DrawableUtil {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    fun clearCursorDrawable(editText: EditText?) {
        if (editText == null) {
            return
        }

        try {
            val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            mCursorDrawableRes.setInt(editText, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getIcon(context: Context, @DrawableRes resource: Int, colorFilter: Int): Drawable? {
        return getIcon(context, resource, colorFilter, PorterDuff.Mode.MULTIPLY)
    }

    fun getIcon(context: Context, @DrawableRes resource: Int, colorFilter: Int, mode: PorterDuff.Mode): Drawable? {
        val iconDrawable = ContextCompat.getDrawable(context, resource)

        if (iconDrawable != null) {
            iconDrawable.clearColorFilter()
            iconDrawable.mutate().setColorFilter(colorFilter, mode)
        }

        return iconDrawable
    }
}