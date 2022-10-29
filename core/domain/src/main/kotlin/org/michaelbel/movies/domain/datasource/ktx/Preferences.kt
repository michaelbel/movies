package org.michaelbel.movies.domain.datasource.ktx

import android.os.Build
import org.michaelbel.movies.ui.theme.model.SystemTheme

fun Int?.orDefaultTheme(): Int {
    return this ?: SystemTheme.FollowSystem.theme
}

fun Boolean?.orDefaultDynamicColorsEnabled(): Boolean {
    return this ?: (Build.VERSION.SDK_INT >= 31)
}

fun Boolean?.orDefaultRtlEnabled(): Boolean {
    return this ?: false
}