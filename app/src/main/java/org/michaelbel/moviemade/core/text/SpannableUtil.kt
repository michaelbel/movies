package org.michaelbel.moviemade.core.text

import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.TypefaceSpan
import timber.log.Timber
import java.util.*

object SpannableUtil {

    @Deprecated("")
    fun boldText(text: String, allText: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(allText)

        val startPos = text.length - 2
        val endPos = allText.length

        spannable.setSpan(TypefaceSpan("sans-serif-medium"), startPos, endPos, SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun replaceTags(str: String): SpannableStringBuilder {
        try {
            var start: Int
            var end: Int
            val stringBuilder = StringBuilder(str)
            val bolds = ArrayList<Int>()

            while (stringBuilder.indexOf("**") != -1) {
                start = stringBuilder.indexOf("**")
                stringBuilder.replace(start, start + 2, "")
                end = stringBuilder.indexOf("**")
                if (end >= 0) {
                    stringBuilder.replace(end, end + 2, "")
                    bolds.add(start)
                    bolds.add(end)
                }
            }
            val spannableStringBuilder = SpannableStringBuilder(stringBuilder)
            for (a in 0 until bolds.size / 2) {
                spannableStringBuilder.setSpan(TypefaceSpan("sans-serif-medium"), bolds[a * 2], bolds[a * 2 + 1], SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            return spannableStringBuilder
        } catch (e: Exception) {
            Timber.e(e)
        }

        return SpannableStringBuilder(str)
    }
}