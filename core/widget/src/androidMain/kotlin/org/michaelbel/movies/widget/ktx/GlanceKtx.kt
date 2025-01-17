package org.michaelbel.movies.widget.ktx

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext

@Composable
internal fun stringResource(@StringRes id: Int, vararg args: Any): String {
    val context = LocalContext.current
    return context.getString(id, args)
}