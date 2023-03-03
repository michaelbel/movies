package org.michaelbel.movies.analytics.constants

import com.google.firebase.analytics.FirebaseAnalytics

internal object MoviesEvents {
    const val SCREEN_VIEW = FirebaseAnalytics.Event.SCREEN_VIEW

    const val SETTINGS_SELECT_LANGUAGE = "select_language"
    const val SETTINGS_SELECT_THEME = "select_theme"
    const val SETTINGS_CHANGE_DYNAMIC_COLORS = "change_dynamic_colors"
    const val SETTINGS_CHANGE_RTL_ENABLED = "change_rtl_enabled"
}