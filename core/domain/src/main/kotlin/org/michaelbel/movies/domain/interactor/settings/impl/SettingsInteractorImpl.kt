package org.michaelbel.movies.domain.interactor.settings.impl

import android.app.NotificationManager
import android.os.Build
import androidx.compose.ui.unit.LayoutDirection
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.event.ChangeDynamicColorsEvent
import org.michaelbel.movies.analytics.event.ChangeRtlEnabledEvent
import org.michaelbel.movies.analytics.event.SelectThemeEvent
import org.michaelbel.movies.common.config.RemoteParams
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.common.googleapi.GoogleApi
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import org.michaelbel.movies.domain.repository.settings.SettingsRepository
import org.michaelbel.movies.ui.theme.model.AppTheme
import org.michaelbel.movies.ui.version.AppVersionData
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SettingsInteractorImpl @Inject constructor(
    @Dispatcher(MoviesDispatchers.Main) private val dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepository,
    notificationManager: NotificationManager,
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
    googleApi: GoogleApi,
    private val analytics: MoviesAnalytics
): SettingsInteractor {

    override val currentTheme: Flow<AppTheme> = settingsRepository.currentTheme

    override val dynamicColors: Flow<Boolean> = settingsRepository.dynamicColors

    override val layoutDirection: Flow<LayoutDirection> = settingsRepository.layoutDirection

    override val areNotificationsEnabled: Boolean = if (Build.VERSION.SDK_INT >= 24) {
        notificationManager.areNotificationsEnabled()
    } else {
        true
    }

    override val isSettingsIconVisible: Flow<Boolean> = flowOf(
        firebaseRemoteConfig.getBoolean(RemoteParams.PARAM_SETTINGS_ICON_VISIBLE)
    )

    override val isPlayServicesAvailable: Flow<Boolean> = flowOf(googleApi.isPlayServicesAvailable)

    override val isAppFromGooglePlay: Flow<Boolean> = flowOf(googleApi.isAppFromGooglePlay)

    override val appVersionData: Flow<AppVersionData> = settingsRepository.appVersionData

    override suspend fun selectTheme(theme: AppTheme) = withContext(dispatcher) {
        settingsRepository.selectTheme(theme)
        analytics.logEvent(SelectThemeEvent(theme.toString()))
    }

    override suspend fun setDynamicColors(value: Boolean) = withContext(dispatcher) {
        settingsRepository.setDynamicColors(value)
        analytics.logEvent(ChangeDynamicColorsEvent(value))
    }

    override suspend fun setRtlEnabled(value: Boolean) = withContext(dispatcher) {
        settingsRepository.setRtlEnabled(value)
        analytics.logEvent(ChangeRtlEnabledEvent(value))
    }

    override suspend fun fetchRemoteConfig() {
        withContext(dispatcher) {
            firebaseRemoteConfig
                .fetchAndActivate()
                .addOnFailureListener(Timber::e)
        }
    }
}