package org.michaelbel.moviemade.presentation.widget

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat

@Suppress("unused")
open class WidgetTextView(context: Context, attrs: AttributeSet?): AppCompatTextView(context, attrs) {

    override fun scrollTo(x: Int, y: Int) {
        // do nothing.
        // https://stackoverflow.com/questions/24027108/how-do-i-completely-prevent-a-textview-from-scrolling
    }

    fun typefaceSpanText(spanned: String) {
        spanText(TypefaceSpan("sans-serif-medium"), spanned)
    }

    fun boldSpanText(spanned: String) {
        spanText(StyleSpan(Typeface.BOLD), spanned)
    }

    fun foregroundSpanText(spanned: String, @ColorRes color: Int) {
        spanText(ForegroundColorSpan(ContextCompat.getColor(context, color)), spanned)
    }

    private fun spanText(tag: Any, spanned: String) {
        if (text.isEmpty() || spanned.isEmpty()) {
            return
        }

        val spannableStringBuilder = SpannableStringBuilder(text)

        var start = text.toString().indexOf(spanned)
        if (start < 0) {
            start = 0 // IndexOutOfBoundsException starts before 0.
        }

        var end = start + spanned.length
        if (end < 0) {
            end = 0
        }

        if (start == 0 && end == 0) {
            return
        }

        spannableStringBuilder.setSpan(tag, start, end, SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannableStringBuilder
    }
}