package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.SelectLanguageEvent
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.entity.AppLanguage

class LocaleInteractorImpl(
    private val dispatchers: SharedDispatchers,
    private val analytics: MoviesAnalytics
): LocaleInteractor {

    override val language: String = AppLanguage.English().code

    override val appLanguage: Flow<AppLanguage> = flowOf(AppLanguage.transform(language))

    override suspend fun selectLanguage(language: AppLanguage) {
        withContext(dispatchers.io) { analytics.logEvent(SelectLanguageEvent(language.toString())) }
    }

    override suspend fun resetLanguage() {}
}
