package org.michaelbel.movies.common.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun String.denied(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        this
    ) == PackageManager.PERMISSION_DENIED
}