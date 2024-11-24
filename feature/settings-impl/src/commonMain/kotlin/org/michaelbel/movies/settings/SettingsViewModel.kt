package org.michaelbel.movies.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.biometric.BiometricController2
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.notify.NotifyManager
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.review.ReviewService
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService

class SettingsViewModel(
    biometricController: BiometricController2,
    private val notifyManager: NotifyManager,
    private val interactor: Interactor,
    private val reviewService: ReviewService,
    private val updateService: UpdateService,
    private val appService: AppService
): BaseViewModel(), DefaultLifecycleObserver {

    val isReviewFeatureEnabled: Boolean
        get() = appService.flavor == Flavor.Gms

    val isUpdateFeatureEnabled: Boolean
        get() = appService.flavor == Flavor.Gms

    val areNotificationsEnabled: Boolean
        get() = notifyManager.areNotificationsEnabled

    val themeData: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = FeedView.FeedList
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = MovieList.NowPlaying()
        )

    val isBiometricFeatureEnabled: StateFlow<Boolean> = biometricController.isBiometricAvailable
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isBiometricEnabled: StateFlow<Boolean> = interactor.isBiometricEnabled
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isScreenshotBlockEnabled: StateFlow<Boolean> = interactor.isScreenshotBlockEnabled
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val appVersionData: StateFlow<AppVersionData> = flowOf(AppVersionData(appService.flavor.name))
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = AppVersionData.Empty
        )

    var isUpdateAvailable by mutableStateOf(false)

    init {
        fetchUpdateAvailable()
    }

    fun selectLanguage(language: AppLanguage) = scope.launch {
        interactor.selectLanguage(language)
    }

    fun selectTheme(theme: AppTheme) = scope.launch {
        interactor.selectTheme(theme)
    }

    fun selectFeedView(feedView: FeedView) = scope.launch {
        interactor.selectFeedView(feedView)
    }

    fun selectMovieList(movieList: MovieList) = scope.launch {
        interactor.selectMovieList(movieList)
    }

    fun setDynamicColors(value: Boolean) = scope.launch {
        interactor.setDynamicColors(value)
    }

    fun setPaletteKey(paletteKey: Int) = scope.launch {
        interactor.setPaletteKey(paletteKey)
    }

    fun setSeedColor(seedColor: Int) = scope.launch {
        interactor.setSeedColor(seedColor)
    }

    fun setBiometricEnabled(enabled: Boolean) = scope.launch {
        interactor.setBiometricEnabled(enabled)
    }

    fun setScreenshotBlockEnabled(enabled: Boolean) = scope.launch {
        interactor.setScreenshotBlockEnabled(enabled)
    }

    fun requestReview(activity: Any) {
        reviewService.requestReview(activity)
    }

    fun requestUpdate(activity: Any) {
        updateService.startUpdate(activity)
    }

    private fun fetchUpdateAvailable() {
        isUpdateAvailable = true
        updateService.setUpdateAvailableListener(object: UpdateListener {
            override fun onAvailable(result: Boolean) {
                isUpdateAvailable = result
            }
        })
    }
}