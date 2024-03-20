@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.analytics.event

import org.michaelbel.movies.analytics.constants.MoviesEvents
import org.michaelbel.movies.analytics.constants.MoviesParams
import org.michaelbel.movies.analytics.model.BaseEvent

actual class SelectLanguageEvent actual constructor(
    language: String
): BaseEvent(MoviesEvents.SETTINGS_SELECT_LANGUAGE) {

    init {
        add(MoviesParams.PARAM_SELECTED_LANGUAGE, language)
    }
}