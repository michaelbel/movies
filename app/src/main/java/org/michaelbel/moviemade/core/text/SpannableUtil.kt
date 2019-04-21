package org.michaelbel.moviemade.core.text

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import androidx.core.content.ContextCompat
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.App

object SpannableUtil {

    fun boldAndColoredText(title: String, allText: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(allText)

        val startPos = 0
        val endPos = title.length - 3 // without ' %s' chars.

        spannable.setSpan(TypefaceSpan("sans-serif-medium"), startPos, endPos, SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(App.appContext, R.color.textColorPrimary)), startPos, endPos, SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannable
    }

    fun boldText(text: String, allText: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(allText)

        val startPos = text.length - 2
        val endPos = allText.length

        spannable.setSpan(TypefaceSpan("sans-serif-medium"), startPos, endPos, SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    @Suppress("unused")
    fun linkText(context: Context, text: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)

        val startPos = 0
        val endPos = text.length

        spannable.setSpan(TypefaceSpan("sans-serif-medium"), startPos, endPos, SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.tmdbPrimary)), startPos, endPos, SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}