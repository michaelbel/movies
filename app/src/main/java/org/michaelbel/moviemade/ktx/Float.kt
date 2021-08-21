@file:Suppress("nothing_to_inline", "unused")

package org.michaelbel.moviemade.ktx

import android.content.Context
import kotlin.math.ceil

inline fun Float.toDp(context: Context): Int = ceil((context.resources.displayMetrics.density * this).toDouble()).toInt()