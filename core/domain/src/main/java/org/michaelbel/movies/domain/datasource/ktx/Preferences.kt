package org.michaelbel.movies.domain.datasource.ktx

import org.michaelbel.movies.ui.SystemTheme

fun Int?.orDefaultTheme(): Int {
    return this ?: SystemTheme.FollowSystem.theme
}

fun Boolean?.orDefaultDynamicColorsEnabled(): Boolean {
    return this ?: false
}