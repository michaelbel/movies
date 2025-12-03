package org.michaelbel.movies.settings

import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.biometric.BiometricInteractor
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.common.notify.NotifyManager
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.SettingsUiInteractor
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService
import org.michaelbel.movies.settings.intent.SettingsIntent
import org.michaelbel.movies.settings.model.SettingsModel
import org.michaelbel.movies.ui.navigation.MainNavigator

class SettingsViewModel(
    val aboutInteractor: AboutInteractor,
    val settingsUiInteractor: SettingsUiInteractor,
    private val biometricController: BiometricInteractor,
    private val notifyManager: NotifyManager,
    private val interactor: Interactor,
    private val updateService: UpdateService,
    private val appService: AppService
): MoviesViewModel<SettingsModel, SettingsIntent>(SettingsModel()), DefaultLifecycleObserver {

    init {
        dispatch(SettingsIntent.CollectThemeData)
        dispatch(SettingsIntent.CollectFeedView)
        dispatch(SettingsIntent.CollectMovieList)
        dispatch(SettingsIntent.CollectAppServiceData)
        dispatch(SettingsIntent.CollectNotificationsEnabled)
        dispatch(SettingsIntent.CollectBiometricFeatureEnabled)
        dispatch(SettingsIntent.CollectBiometricEnabled)
        dispatch(SettingsIntent.CollectScreenshotBlockEnabled)
        dispatch(SettingsIntent.FetchUpdateAvailable)
    }

    override fun dispatch(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.CollectThemeData -> {
                launch {
                    interactor.themeData.collectLatest { themeData ->
                        reduce { it.copy(themeData = themeData) }
                    }
                }
            }
            is SettingsIntent.CollectFeedView -> {
                launch {
                    interactor.currentFeedView.collectLatest { feedView ->
                        reduce { it.copy(feedView = feedView) }
                    }
                }
            }
            is SettingsIntent.CollectMovieList -> {
                launch {
                    interactor.currentMovieList.collectLatest { movieList ->
                        reduce { it.copy(movieList = movieList) }
                    }
                }
            }
            is SettingsIntent.CollectAppServiceData -> {
                reduce { it.copy(isReviewFeatureEnabled = appService.flavor == Flavor.Gms, isUpdateFeatureEnabled = appService.flavor == Flavor.Gms, appVersionData = AppVersionData(appService.flavor.name)) }
            }
            is SettingsIntent.CollectNotificationsEnabled -> {
                reduce { it.copy(areNotificationsEnabled = notifyManager.areNotificationsEnabled) }
            }
            is SettingsIntent.CollectBiometricFeatureEnabled -> {
                launch {
                    biometricController.isBiometricAvailable.collectLatest { isBiometricFeatureEnabled ->
                        reduce { it.copy(isBiometricFeatureEnabled = isBiometricFeatureEnabled) }
                    }
                }
            }
            is SettingsIntent.CollectBiometricEnabled -> {
                launch {
                    interactor.isBiometricEnabled.collectLatest { isBiometricEnabled ->
                        reduce { it.copy(isBiometricEnabled = isBiometricEnabled) }
                    }
                }
            }
            is SettingsIntent.CollectScreenshotBlockEnabled -> {
                launch {
                    interactor.isScreenshotBlockEnabled.collectLatest { isScreenshotBlockEnabled ->
                        reduce { it.copy(isScreenshotBlockEnabled = isScreenshotBlockEnabled) }
                    }
                }
            }
            is SettingsIntent.FetchUpdateAvailable -> {
                reduce { it.copy(isUpdateAvailable = true) }
                updateService.setUpdateAvailableListener(object: UpdateListener {
                    override fun onAvailable(result: Boolean) {
                        reduce { it.copy(isUpdateAvailable = result) }
                    }
                })
            }
            is SettingsIntent.BackClick -> launch { MainNavigator.back() }
            is SettingsIntent.ReviewClick -> launch { MainNavigator.requestReview() }
            is SettingsIntent.UpdateClick -> launch { MainNavigator.requestUpdate() }
            is SettingsIntent.SelectLanguage -> launch { interactor.selectLanguage(intent.language) }
            is SettingsIntent.SelectTheme -> launch { interactor.selectTheme(intent.theme) }
            is SettingsIntent.SelectFeedView -> launch { interactor.selectFeedView(intent.feedView) }
            is SettingsIntent.SelectMovieList -> launch { interactor.selectMovieList(intent.movieList) }
            is SettingsIntent.SetDynamicColors -> launch { interactor.setDynamicColors(intent.value) }
            is SettingsIntent.SetPaletteKey -> launch { interactor.setPaletteKey(intent.paletteKey) }
            is SettingsIntent.SetSeedColor -> launch { interactor.setSeedColor(intent.seedColor) }
            is SettingsIntent.SetBiometricEnabled -> launch { interactor.setBiometricEnabled(intent.enabled) }
            is SettingsIntent.SetScreenshotBlockEnabled -> launch { interactor.setScreenshotBlockEnabled(intent.enabled) }
            is SettingsIntent.SetUpdateAvailable -> { reduce { it.copy(isUpdateAvailable = intent.state) } }
        }
    }
}