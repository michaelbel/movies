package org.michaelbel.movies.analytics.model

import com.google.firebase.analytics.FirebaseAnalytics

object MoviesEvents {
    const val SCREEN_VIEW = FirebaseAnalytics.Event.SCREEN_VIEW

    const val SETTINGS_SELECT_THEME = "select_theme"
    const val SETTINGS_CHANGE_DYNAMIC_COLORS = "change_dynamic_colors"
}