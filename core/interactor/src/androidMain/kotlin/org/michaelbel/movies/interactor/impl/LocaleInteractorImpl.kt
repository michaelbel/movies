package org.michaelbel.movies.interactor.impl

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.SelectLanguageEvent
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.entity.AppLanguage

internal class LocaleInteractorImpl(
    private val context: Context,
    private val dispatchers: MoviesDispatchers,
    private val analytics: MoviesAnalytics
): LocaleInteractor {

    override val language: String
        get() = AppCompatDelegate.getApplicationLocales()[0]?.language ?: AppLanguage.English().code

    override val appLanguage: Flow<AppLanguage> = flowOf(AppLanguage.transform(language))

    override suspend fun selectLanguage(language: AppLanguage) {
        withContext(dispatchers.io) {
            // for AppCompatActivity
            /*AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.code))*/

            // for ComponentActivity
            if (Build.VERSION.SDK_INT >= 33) {
                val localeManager = context.getSystemService(LocaleManager::class.java)
                localeManager.applicationLocales = LocaleList.forLanguageTags(AppLanguage.code(language))
            } else {
                val locale = Locale(AppLanguage.code(language))
                Locale.setDefault(locale)

                val configuration = context.resources.configuration
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            }

            analytics.logEvent(SelectLanguageEvent(language.toString()))
        }
    }
}