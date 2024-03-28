@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.common.localization.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.localization.model.AppLanguage

internal expect class LocaleControllerImpl {

    val language: String

    val appLanguage: Flow<AppLanguage>

    suspend fun selectLanguage(language: AppLanguage)
}