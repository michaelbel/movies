package org.michaelbel.movies.common.localization.impl

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.SelectLanguageEvent
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.common.localization.model.AppLanguage
import java.util.Locale
import javax.inject.Inject

internal class LocaleControllerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatchers: MoviesDispatchers,
    private val analytics: MoviesAnalytics
): LocaleController {

    override val language: String
        get() = AppCompatDelegate.getApplicationLocales()[0]?.language ?: AppLanguage.English.code

    override val appLanguage: Flow<AppLanguage> = flowOf(AppLanguage.transform(language))

    override suspend fun selectLanguage(language: AppLanguage) {
        withContext(dispatchers.io) {
            // for AppCompatActivity
            /*AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.code))*/

            // for ComponentActivity
            if (Build.VERSION.SDK_INT >= 33) {
                val localeManager = context.getSystemService(LocaleManager::class.java)
                localeManager.applicationLocales = LocaleList.forLanguageTags(language.code)
            } else {
                val locale = Locale(language.code)
                Locale.setDefault(locale)

                val configuration = context.resources.configuration
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            }

            analytics.logEvent(SelectLanguageEvent(language.toString()))
        }
    }
}