package org.michaelbel.moviemade.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import android.content.res.TypedArray
import androidx.annotation.AttrRes
import androidx.annotation.NonNull
import org.michaelbel.moviemade.R


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

    fun getAttrColor(@NonNull context: Context, @AttrRes colorAttr: Int): Int {
        var color = 0
        val attrs = intArrayOf(colorAttr)

        try {
            val typedArray = context.obtainStyledAttributes(attrs)
            color = typedArray.getColor(0, 0)
            typedArray.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return color
    }

    fun selectableItemBackground(context: Context): Int {
        val attrs = intArrayOf(R.attr.selectableItemBackground)
        val typedArray = context.obtainStyledAttributes(attrs)
        val backgroundResource = typedArray.getResourceId(0, 0)
        typedArray.recycle()
        return backgroundResource
    }

    fun selectableItemBackgroundBorderless(context: Context): Int {
        val attrs = intArrayOf(R.attr.selectableItemBackgroundBorderless)
        val typedArray = context.obtainStyledAttributes(attrs)
        val backgroundResource = typedArray.getResourceId(0, 0)
        typedArray.recycle()
        return backgroundResource
    }

    fun selectableItemBackgroundDrawable(context: Context): Drawable? {
        val attrs = intArrayOf(android.R.attr.selectableItemBackground)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawableFromTheme = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawableFromTheme
    }

    fun selectableItemBackgroundBorderlessDrawable(context: Context): Drawable? {
        val attrs = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
        val typedArray = context.obtainStyledAttributes(attrs)
        val drawableFromTheme = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawableFromTheme
    }
}