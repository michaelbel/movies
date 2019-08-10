package org.michaelbel.moviemade.presentation.widget

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText

@Suppress("unused")
open class WidgetEditText: AppCompatEditText {

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init()
    }

    private fun init() {}

    fun clearCursorDrawable() {
        val cursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        cursorDrawableRes.isAccessible = true
        cursorDrawableRes.setInt(this, 0)
    }

    fun showKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, SHOW_IMPLICIT)
    }

    fun hideKeyboard() {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isActive) {
            return
        }
        imm.hideSoftInputFromWindow(this.windowToken, 0)
    }
}