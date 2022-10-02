package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.model.AnalyticsEvents
import org.michaelbel.movies.analytics.model.BaseEvent

class ChangeDynamicColorsEvent(
    enabled: Boolean
): BaseEvent(AnalyticsEvents.SETTINGS_CHANGE_DYNAMIC_COLORS) {

    init {
        add(AnalyticsEvents.PARAM_DYNAMIC_COLORS_ENABLED, enabled)
    }
}