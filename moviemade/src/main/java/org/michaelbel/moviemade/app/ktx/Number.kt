package org.michaelbel.moviemade.app.ktx

import android.content.Context
import android.util.TypedValue

fun Int.dp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    toFloat(),
    context.resources.displayMetrics
).toInt()