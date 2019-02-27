package org.michaelbel.moviemade.core.utils

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import org.michaelbel.moviemade.R

object ViewUtil {

    @RequiresApi(12)
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

    fun getIcon(context: Context, @DrawableRes resource: Int, colorFilter: Int): Drawable? =
        getIcon(context, resource, colorFilter, PorterDuff.Mode.MULTIPLY)

    private fun getIcon(context: Context, @DrawableRes resource: Int, colorFilter: Int, mode: PorterDuff.Mode): Drawable? {
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setElevation(view: View, elevation : Float) {
        val dur = view.resources.getInteger(R.integer.app_bar_elevation_anim_duration)
        val sla = StateListAnimator()
        sla.addState(intArrayOf(16842766, R.attr.state_liftable, -R.attr.state_lifted), ObjectAnimator.ofFloat(view, "elevation", 0.0f).setDuration(dur.toLong()))
        sla.addState(intArrayOf(16842766), ObjectAnimator.ofFloat(view, "elevation", elevation).setDuration(dur.toLong()))
        sla.addState(IntArray(0), ObjectAnimator.ofFloat(view, "elevation", 0.0f).setDuration(0L))
        view.stateListAnimator = sla
    }
}