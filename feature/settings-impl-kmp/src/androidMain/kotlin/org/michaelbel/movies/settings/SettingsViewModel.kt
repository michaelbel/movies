package org.michaelbel.movies.settings

import android.app.Activity
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
import org.michaelbel.movies.common.biometric.BiometricController
import org.michaelbel.movies.common.list.MovieList
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
import org.michaelbel.movies.settings_impl_kmp.BuildConfig

class SettingsViewModel(
    biometricController: BiometricController,
    private val interactor: Interactor,
    private val reviewService: ReviewService,
    private val updateService: UpdateService,
    appService: AppService
): BaseViewModel(), DefaultLifecycleObserver {

    val isReviewFeatureEnabled = appService.flavor == Flavor.Gms

    val isUpdateFeatureEnabled = appService.flavor == Flavor.Gms

    val themeData: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = FeedView.FeedList
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = MovieList.NowPlaying()
        )

    val isBiometricFeatureEnabled: StateFlow<Boolean> = biometricController.isBiometricAvailable
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isBiometricEnabled: StateFlow<Boolean> = interactor.isBiometricEnabled
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isScreenshotBlockEnabled: StateFlow<Boolean> = interactor.isScreenshotBlockEnabled
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val appVersionData: StateFlow<AppVersionData> = flowOf(AppVersionData(appService.flavor.name))
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppVersionData.Empty
        )

    var isUpdateAvailable by mutableStateOf(false)

    init {
        fetchUpdateAvailable()
    }

    fun selectLanguage(language: AppLanguage) = launch {
        interactor.selectLanguage(language)
    }

    fun selectTheme(theme: AppTheme) = launch {
        interactor.selectTheme(theme)
    }

    fun selectFeedView(feedView: FeedView) = launch {
        interactor.selectFeedView(feedView)
    }

    fun selectMovieList(movieList: MovieList) = launch {
        interactor.selectMovieList(movieList)
    }

    fun setDynamicColors(value: Boolean) = launch {
        interactor.setDynamicColors(value)
    }

    fun setPaletteKey(paletteKey: Int) = launch {
        interactor.setPaletteKey(paletteKey)
    }

    fun setSeedColor(seedColor: Int) = launch {
        interactor.setSeedColor(seedColor)
    }

    fun setBiometricEnabled(enabled: Boolean) = launch {
        interactor.setBiometricEnabled(enabled)
    }

    fun setScreenshotBlockEnabled(enabled: Boolean) = launch {
        interactor.setScreenshotBlockEnabled(enabled)
    }

    fun requestReview(activity: Activity) {
        reviewService.requestReview(activity)
    }

    fun requestUpdate(activity: Activity) {
        updateService.startUpdate(activity)
    }

    private fun fetchUpdateAvailable() {
        isUpdateAvailable = BuildConfig.DEBUG
        updateService.setUpdateAvailableListener(object: UpdateListener {
            override fun onAvailable(result: Boolean) {
                isUpdateAvailable = result
            }
        })
    }
}