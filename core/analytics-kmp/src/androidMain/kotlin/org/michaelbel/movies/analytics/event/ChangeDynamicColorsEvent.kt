@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

actual class ChangeDynamicColorsEvent actual constructor(
    enabled: Boolean
): BaseEvent(MoviesEvents.SETTINGS_CHANGE_DYNAMIC_COLORS) {

    init {
        add(MoviesParams.PARAM_DYNAMIC_COLORS_ENABLED, enabled)
    }
}