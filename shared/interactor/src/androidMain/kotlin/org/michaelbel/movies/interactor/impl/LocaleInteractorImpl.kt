package org.michaelbel.movies.interactor.impl

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.SelectLanguageEvent
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.entity.AppLanguage
import java.util.Locale

class LocaleInteractorImpl(
    private val context: Context,
    private val dispatchers: SharedDispatchers,
    private val analytics: MoviesAnalytics
): LocaleInteractor {

    private fun String?.supportedLanguageCode(): String? {
        return takeIf { it == AppLanguage.English().code || it == AppLanguage.Russian().code }
    }

    override val language: String
        get() {
            val appCompatLocales = AppCompatDelegate.getApplicationLocales()
            val appCompatLanguage = if (appCompatLocales.size() > 0) appCompatLocales[0]?.language else null
            appCompatLanguage.supportedLanguageCode()?.let { return it }

            if (Build.VERSION.SDK_INT >= 33) {
                val localeManager = ContextCompat.getSystemService(context, LocaleManager::class.java)
                if (localeManager != null) {
                    val frameworkLocales = localeManager.applicationLocales
                    val frameworkLanguage = if (frameworkLocales.size() > 0) frameworkLocales[0].language else null
                    frameworkLanguage.supportedLanguageCode()?.let { return it }
                }
            }

            val resourcesLocales = context.resources.configuration.locales
            val resourcesLanguage = if (resourcesLocales.size() > 0) resourcesLocales[0].language else null
            return resourcesLanguage.supportedLanguageCode() ?: AppLanguage.English().code
        }

    override val appLanguage: Flow<AppLanguage> = flowOf(AppLanguage.transform(language))

    override suspend fun selectLanguage(language: AppLanguage) {
        withContext(dispatchers.io) {
            val languageCode = AppLanguage.code(language)
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
            when {
                Build.VERSION.SDK_INT >= 33 -> {
                    val localeManager = ContextCompat.getSystemService(context, LocaleManager::class.java)
                    localeManager?.applicationLocales = LocaleList.forLanguageTags(languageCode)
                }
                else -> {
                    @Suppress("DEPRECATION") val locale = Locale(languageCode)
                    Locale.setDefault(locale)
                    val configuration = context.resources.configuration
                    configuration.setLocale(locale)
                    context.createConfigurationContext(configuration)
                }
            }
            analytics.logEvent(SelectLanguageEvent(language.toString()))
        }
    }

    override suspend fun resetLanguage() {
        withContext(dispatchers.io) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
            when {
                Build.VERSION.SDK_INT >= 33 -> {
                    val localeManager = ContextCompat.getSystemService(context, LocaleManager::class.java)
                    localeManager?.applicationLocales = LocaleList.getEmptyLocaleList()
                }
                else -> {
                    val locale = Locale.getDefault()
                    Locale.setDefault(locale)
                    val configuration = context.resources.configuration
                    configuration.setLocale(locale)
                    context.createConfigurationContext(configuration)
                }
            }
        }
    }
}
