package org.michaelbel.movies.settings.ktx

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun String.denied(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        this
    ) == PackageManager.PERMISSION_DENIED
}