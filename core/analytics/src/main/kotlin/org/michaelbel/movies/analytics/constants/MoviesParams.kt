package org.michaelbel.movies.analytics.constants

import com.google.firebase.analytics.FirebaseAnalytics

internal object MoviesParams {
    const val PARAM_DESTINATION = FirebaseAnalytics.Param.SCREEN_NAME
    const val PARAM_ARGUMENTS = "destination_arguments"

    const val PARAM_SELECTED_LANGUAGE = "selected_language"
    const val PARAM_SELECTED_THEME = "selected_theme"
    const val PARAM_SELECTED_FEED_VIEW = "selected_feed_view"
    const val PARAM_DYNAMIC_COLORS_ENABLED = "dynamic_colors_enabled"
    const val PARAM_RTL_ENABLED = "rtl_enabled"
}