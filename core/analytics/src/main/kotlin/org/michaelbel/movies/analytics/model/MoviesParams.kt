package org.michaelbel.movies.analytics.model

import com.google.firebase.analytics.FirebaseAnalytics

object MoviesParams {
    const val PARAM_DESTINATION = FirebaseAnalytics.Param.SCREEN_NAME
    const val PARAM_ARGUMENTS = "destination_arguments"

    const val PARAM_SELECTED_THEME = "selected_theme"
    const val PARAM_DYNAMIC_COLORS_ENABLED = "dynamic_colors_enabled"
}