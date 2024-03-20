@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

actual class SelectThemeEvent actual constructor(
    theme: String
): BaseEvent(MoviesEvents.SETTINGS_SELECT_THEME) {

    init {
        add(MoviesParams.PARAM_SELECTED_THEME, theme)
    }
}