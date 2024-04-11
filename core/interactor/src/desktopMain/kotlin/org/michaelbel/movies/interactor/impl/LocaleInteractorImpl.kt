@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.entity.AppLanguage

internal actual class LocaleInteractorImpl(
    private val dispatchers: MoviesDispatchers
): LocaleInteractor {

    actual override val language: String
        get() = AppLanguage.English().code

    actual override val appLanguage: Flow<AppLanguage> = flowOf(AppLanguage.transform(language))

    actual override suspend fun selectLanguage(language: AppLanguage) {
        withContext(dispatchers.io) {}
    }
}