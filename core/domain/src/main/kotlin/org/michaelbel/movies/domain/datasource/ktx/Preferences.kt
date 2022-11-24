package org.michaelbel.movies.domain.datasource.ktx

import android.os.Build
import org.michaelbel.movies.ui.theme.model.AppTheme

internal fun Int?.orDefaultTheme(): Int {
    return this ?: AppTheme.FollowSystem.theme
}

internal fun Boolean?.orDefaultDynamicColorsEnabled(): Boolean {
    return this ?: (Build.VERSION.SDK_INT >= 31)
}

internal fun Boolean?.orDefaultRtlEnabled(): Boolean {
    return this ?: false
}