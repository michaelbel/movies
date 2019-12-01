@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context
import android.util.TypedValue
import android.util.TypedValue.COMPLEX_UNIT_DIP

inline fun Int.toDp(context: Context): Int = TypedValue.applyDimension(COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()

inline fun Int.isBetween(int1: Int, int2: Int): Boolean = this in (int1 + 1) until int2

infix fun Int.isBetween(range: IntRange): Boolean = this in range.first until range.last