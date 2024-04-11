@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.interactor.entity.AppLanguage

internal expect class LocaleInteractorImpl {

    val language: String

    val appLanguage: Flow<AppLanguage>

    suspend fun selectLanguage(
        language: AppLanguage
    )
}