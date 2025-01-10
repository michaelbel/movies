package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.interactor.entity.AppLanguage

interface LocaleInteractor {

    val language: String

    val appLanguage: Flow<AppLanguage>

    suspend fun selectLanguage(
        language: AppLanguage
    )
}