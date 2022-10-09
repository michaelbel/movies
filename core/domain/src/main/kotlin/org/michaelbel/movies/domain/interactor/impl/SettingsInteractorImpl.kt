package org.michaelbel.movies.domain.interactor.impl

import android.app.NotificationManager
import android.os.Build
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.michaelbel.movies.analytics.Analytics
import org.michaelbel.movies.analytics.event.ChangeDynamicColorsEvent
import org.michaelbel.movies.analytics.event.SelectThemeEvent
import org.michaelbel.movies.common.config.RemoteParams
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.domain.repository.SettingsRepository
import org.michaelbel.movies.ui.theme.SystemTheme

class SettingsInteractorImpl @Inject constructor(
    @Dispatcher(MoviesDispatchers.Main) private val dispatcher: CoroutineDispatcher,
    private val settingsRepository: SettingsRepository,
    private val notificationManager: NotificationManager,
    firebaseRemoteConfig: FirebaseRemoteConfig,
    private val analytics: Analytics
): SettingsInteractor {

    override val currentTheme: Flow<SystemTheme> = settingsRepository.currentTheme

    override val dynamicColors: Flow<Boolean> = settingsRepository.dynamicColors

    override val areNotificationsEnabled: Boolean
        get() = if (Build.VERSION.SDK_INT >= 24) {
            notificationManager.areNotificationsEnabled()
        } else {
            true
        }

    override val isSettingsIconVisible: Flow<Boolean> = flowOf(
        firebaseRemoteConfig.getBoolean(RemoteParams.PARAM_SETTINGS_ICON_VISIBLE)
    )

    override suspend fun selectTheme(systemTheme: SystemTheme) = withContext(dispatcher) {
        settingsRepository.selectTheme(systemTheme)
        analytics.logEvent(SelectThemeEvent(systemTheme.toString()))
    }

    override suspend fun setDynamicColors(value: Boolean) = withContext(dispatcher) {
        settingsRepository.setDynamicColors(value)
        analytics.logEvent(ChangeDynamicColorsEvent(value))
    }
}