package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.model.AnalyticsEvents
import org.michaelbel.movies.analytics.model.BaseEvent

class SelectThemeEvent(
    theme: String
): BaseEvent(AnalyticsEvents.SETTINGS_SELECT_THEME) {

    init {
        add(AnalyticsEvents.PARAM_SELECTED_THEME, theme)
    }
}