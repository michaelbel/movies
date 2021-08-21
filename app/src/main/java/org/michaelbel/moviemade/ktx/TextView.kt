@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.text.InputFilter
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.widget.TextView
import androidx.annotation.IntRange

inline fun TextView.setMaxLength(@IntRange(from = 0, to = Long.MAX_VALUE) value: Int) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(value))
}

inline fun TextView.setTextSizeSp(size: Float) {
    setTextSize(COMPLEX_UNIT_SP, size)
}