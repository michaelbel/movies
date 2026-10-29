package org.michaelbel.movies.settings

import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.biometric.BiometricInteractor
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.common.notify.NotifyManager
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.domain.usecase.BiometricEnabledFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentFeedViewFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentMovieListFlowUseCase
import org.michaelbel.movies.domain.usecase.CurrentTraktListFlowUseCase
import org.michaelbel.movies.domain.usecase.ProviderFlowUseCase
import org.michaelbel.movies.domain.usecase.ResetSettingsUseCase
import org.michaelbel.movies.domain.usecase.ScreenshotBlockEnabledFlowUseCase
import org.michaelbel.movies.domain.usecase.SelectFeedViewUseCase
import org.michaelbel.movies.domain.usecase.SelectMovieListUseCase
import org.michaelbel.movies.domain.usecase.SelectThemeUseCase
import org.michaelbel.movies.domain.usecase.SelectTraktListUseCase
import org.michaelbel.movies.domain.usecase.SetBiometricEnabledUseCase
import org.michaelbel.movies.domain.usecase.SetDynamicColorsUseCase
import org.michaelbel.movies.domain.usecase.SetPaletteColorsUseCase
import org.michaelbel.movies.domain.usecase.SetPaletteKeyUseCase
import org.michaelbel.movies.domain.usecase.SetProviderUseCase
import org.michaelbel.movies.domain.usecase.SetScreenshotBlockEnabledUseCase
import org.michaelbel.movies.domain.usecase.SetSeedColorUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.platform.Flavor
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService
import org.michaelbel.movies.settings.event.SettingsEvent
import org.michaelbel.movies.settings.intent.SettingsIntent
import org.michaelbel.movies.settings.model.SettingsModel
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.navigation.DebugDestination
import org.michaelbel.movies.ui.navigation.MainNavigator

class SettingsViewModel(
    private val uiInteractor: UiInteractor,
    private val aboutInteractor: AboutInteractor,
    private val biometricController: BiometricInteractor,
    private val notifyManager: NotifyManager,
    private val interactor: Interactor,
    private val updateService: UpdateService,
    private val appService: AppService,
    private val selectThemeUseCase: SelectThemeUseCase,
    private val selectFeedViewUseCase: SelectFeedViewUseCase,
    private val selectMovieListUseCase: SelectMovieListUseCase,
    private val selectTraktListUseCase: SelectTraktListUseCase,
    private val setDynamicColorsUseCase: SetDynamicColorsUseCase,
    private val resetSettingsUseCase: ResetSettingsUseCase,
    private val themeDataFlowUseCase: ThemeDataFlowUseCase,
    private val currentFeedViewFlowUseCase: CurrentFeedViewFlowUseCase,
    private val currentMovieListFlowUseCase: CurrentMovieListFlowUseCase,
    private val currentTraktListFlowUseCase: CurrentTraktListFlowUseCase,
    private val providerFlowUseCase: ProviderFlowUseCase,
    private val biometricEnabledFlowUseCase: BiometricEnabledFlowUseCase,
    private val screenshotBlockEnabledFlowUseCase: ScreenshotBlockEnabledFlowUseCase,
    private val setPaletteColorsUseCase: SetPaletteColorsUseCase,
    private val setPaletteKeyUseCase: SetPaletteKeyUseCase,
    private val setSeedColorUseCase: SetSeedColorUseCase,
    private val setProviderUseCase: SetProviderUseCase,
    private val setBiometricEnabledUseCase: SetBiometricEnabledUseCase,
    private val setScreenshotBlockEnabledUseCase: SetScreenshotBlockEnabledUseCase
): MoviesViewModel<SettingsModel, SettingsIntent, SettingsEvent>(SettingsModel()), DefaultLifecycleObserver {

    init {
        dispatch(SettingsIntent.CollectThemeData)
        dispatch(SettingsIntent.CollectFeedView)
        dispatch(SettingsIntent.CollectMovieList)
        dispatch(SettingsIntent.CollectTraktList)
        dispatch(SettingsIntent.CollectProvider)
        dispatch(SettingsIntent.CollectAppServiceData)
        dispatch(SettingsIntent.CollectNotificationsEnabled)
        dispatch(SettingsIntent.CollectDoNotDisturbState)
        dispatch(SettingsIntent.CollectIgnoringBatteryOptimizations)
        dispatch(SettingsIntent.CollectBiometricFeatureEnabled)
        dispatch(SettingsIntent.CollectBiometricEnabled)
        dispatch(SettingsIntent.CollectScreenshotBlockEnabled)
        dispatch(SettingsIntent.CollectGender)
        dispatch(SettingsIntent.CollectAbout)
        dispatch(SettingsIntent.CollectFeaturesEnabled)
        dispatch(SettingsIntent.CollectAppIcon)
        dispatch(SettingsIntent.FetchUpdateAvailable)
    }

    override fun dispatch(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.CollectThemeData -> {
                launch {
                    themeDataFlowUseCase(uiInteractor.defaultDynamicColorsEnabled).collectLatest { data ->
                        reduce { it.copy(themeData = data) }
                    }
                }
            }
            is SettingsIntent.CollectFeedView -> {
                launch {
                    currentFeedViewFlowUseCase(Unit).collectLatest { feedView ->
                        reduce { it.copy(feedView = feedView) }
                    }
                }
            }
            is SettingsIntent.CollectMovieList -> {
                launch {
                    currentMovieListFlowUseCase(Unit).collectLatest { movieList ->
                        reduce { it.copy(movieList = movieList) }
                    }
                }
            }
            is SettingsIntent.CollectTraktList -> {
                launch {
                    currentTraktListFlowUseCase(Unit).collectLatest { traktList ->
                        reduce { it.copy(traktList = traktList) }
                    }
                }
            }
            is SettingsIntent.CollectProvider -> {
                launch {
                    providerFlowUseCase(Unit).collectLatest { key ->
                        reduce { it.copy(provider = AppProvider.transform(key)) }
                    }
                }
            }
            is SettingsIntent.CollectAppServiceData -> {
                reduce { it.copy(
                    isReviewFeatureEnabled = appService.flavor == Flavor.Gms,
                    isUpdateFeatureEnabled = appService.flavor == Flavor.Gms,
                    isGooglePlayFeatureEnabled = appService.flavor == Flavor.Gms,
                    appVersionData = AppVersionData(appService.flavor.name))
                }
            }
            is SettingsIntent.CollectNotificationsEnabled -> {
                reduce { it.copy(areNotificationsEnabled = notifyManager.areNotificationsEnabled) }
            }
            is SettingsIntent.CollectDoNotDisturbState -> {
                reduce {
                    it.copy(
                        isDoNotDisturbAccessGranted = notifyManager.isDoNotDisturbAccessGranted,
                        isDoNotDisturbEnabled = notifyManager.isDoNotDisturbEnabled
                    )
                }
            }
            is SettingsIntent.CollectIgnoringBatteryOptimizations -> {
                reduce { it.copy(isIgnoringBatteryOptimizations = uiInteractor.isIgnoringBatteryOptimizations) }
            }
            is SettingsIntent.CollectBiometricFeatureEnabled -> {
                launch {
                    biometricController.isBiometricAvailable.collectLatest { isBiometricAvailable ->
                        reduce { it.copy(isBiometricAvailable = isBiometricAvailable) }
                    }
                }
            }
            is SettingsIntent.CollectBiometricEnabled -> {
                launch {
                    biometricEnabledFlowUseCase(Unit).collectLatest { isBiometricEnabled ->
                        reduce { it.copy(isBiometricEnabled = isBiometricEnabled) }
                    }
                }
            }
            is SettingsIntent.CollectScreenshotBlockEnabled -> {
                launch {
                    screenshotBlockEnabledFlowUseCase(Unit).collectLatest { isScreenshotBlockEnabled ->
                        reduce { it.copy(isScreenshotBlockEnabled = isScreenshotBlockEnabled) }
                    }
                }
            }
            is SettingsIntent.CollectGender -> {
                reduce { it.copy(grammaticalGender = uiInteractor.grammaticalGender) }
            }
            is SettingsIntent.CollectAbout -> {
                reduce { it.copy(
                    versionName = aboutInteractor.versionName,
                    versionCode = aboutInteractor.versionCode)
                }
            }
            is SettingsIntent.CollectFeaturesEnabled -> {
                reduce {
                    it.copy(
                        isLanguageFeatureEnabled = uiInteractor.isLanguageFeatureEnabled,
                        isThemeFeatureEnabled = uiInteractor.isThemeFeatureEnabled,
                        isFeedViewFeatureEnabled = uiInteractor.isFeedViewFeatureEnabled,
                        isMovieListFeatureEnabled = uiInteractor.isMovieListFeatureEnabled,
                        isGenderFeatureEnabled = uiInteractor.isGenderFeatureEnabled,
                        isDynamicColorsFeatureEnabled = uiInteractor.isDynamicColorsFeatureEnabled,
                        isPaletteColorsFeatureEnabled = uiInteractor.isPaletteColorsFeatureEnabled,
                        isNotificationsFeatureEnabled = uiInteractor.isNotificationsFeatureEnabled,
                        isDoNotDisturbFeatureEnabled = uiInteractor.isDoNotDisturbFeatureEnabled,
                        isBatteryOptimizationFeatureEnabled = uiInteractor.isBatteryOptimizationFeatureEnabled,
                        isBiometricFeatureEnabled = uiInteractor.isBiometricFeatureEnabled,
                        isWidgetFeatureEnabled = uiInteractor.isWidgetFeatureEnabled,
                        isTileFeatureEnabled = uiInteractor.isTileFeatureEnabled,
                        isAppIconFeatureEnabled = uiInteractor.isAppIconFeatureEnabled,
                        isAppOpenByDefaultFeatureEnabled = uiInteractor.isAppOpenByDefaultFeatureEnabled,
                        isScreenshotFeatureEnabled = uiInteractor.isScreenshotFeatureEnabled,
                        isGithubFeatureEnabled = uiInteractor.isGithubFeatureEnabled,
                        isTelegramFeatureEnabled = uiInteractor.isTelegramFeatureEnabled,
                        isReviewAppFeatureEnabled = uiInteractor.isReviewAppFeatureEnabled,
                        isUpdateAppFeatureEnabled = uiInteractor.isUpdateAppFeatureEnabled,
                        isAboutFeatureEnabled = uiInteractor.isAboutFeatureEnabled,
                        isSettingsResetFeatureEnabled = uiInteractor.isSettingsResetFeatureEnabled,
                        isDebugDialogFeatureEnabled = uiInteractor.isDebugDialogFeatureEnabled,
                    )
                }
            }
            is SettingsIntent.CollectAppIcon -> {
                reduce { it.copy(enabledIcon = uiInteractor.enabledIcon) }
            }
            is SettingsIntent.FetchUpdateAvailable -> {
                updateService.setUpdateAvailableListener(object: UpdateListener {
                    override fun onAvailable(result: Boolean) {
                        reduce { it.copy(isUpdateAvailable = result) }
                    }
                })
            }
            is SettingsIntent.RequestPostNotificationsPermission -> {
                launch { push(SettingsEvent.RequestPostNotificationsPermission) }
            }
            is SettingsIntent.RequestDoNotDisturbAccess -> {
                launch { push(SettingsEvent.RequestDoNotDisturbAccess) }
            }
            is SettingsIntent.RequestIgnoreBatteryOptimizations -> {
                launch { push(SettingsEvent.RequestIgnoreBatteryOptimizations) }
            }
            is SettingsIntent.RequestTileService -> launch { push(SettingsEvent.RequestTileService) }
            is SettingsIntent.RequestGithub -> launch { push(SettingsEvent.RequestGithub) }
            is SettingsIntent.RequestTelegram -> launch { push(SettingsEvent.RequestTelegram) }
            is SettingsIntent.RequestGooglePlay -> launch { push(SettingsEvent.RequestGooglePlay) }
            is SettingsIntent.OpenDebugDialog -> launch { MainNavigator.forward(DebugDestination) }
            is SettingsIntent.BackClick -> launch { MainNavigator.back() }
            is SettingsIntent.ReviewClick -> launch { MainNavigator.requestReview() }
            is SettingsIntent.UpdateClick -> launch { MainNavigator.requestUpdate() }
            is SettingsIntent.ScrollToTop -> launch { push(SettingsEvent.ScrollToTop) }
            is SettingsIntent.ShowSnackbar -> launch { push(SettingsEvent.ShowSnackbar(intent.message)) }
            is SettingsIntent.ShowPermissionSnackbar -> {
                launch { push(SettingsEvent.ShowPermissionSnackbar(intent.message, intent.actionLabel)) }
            }
            is SettingsIntent.SelectLanguage -> launch { interactor.selectLanguage(intent.language) }
            is SettingsIntent.SelectTheme -> launch { selectThemeUseCase(intent.theme).getOrThrow() }
            is SettingsIntent.SelectFeedView -> launch { selectFeedViewUseCase(intent.feedView).getOrThrow() }
            is SettingsIntent.SelectMovieList -> launch { selectMovieListUseCase(intent.movieList).getOrThrow() }
            is SettingsIntent.SelectTraktList -> {
                launch { selectTraktListUseCase(intent.traktList).getOrThrow() }
            }
            is SettingsIntent.SelectProvider -> {
                launch { setProviderUseCase(AppProvider.key(intent.provider)).getOrThrow() }
            }
            is SettingsIntent.SetDynamicColors -> launch { setDynamicColorsUseCase(intent.value).getOrThrow() }
            is SettingsIntent.SetPaletteColors -> launch { setPaletteColorsUseCase(intent.value).getOrThrow() }
            is SettingsIntent.SetPaletteKey -> launch { setPaletteKeyUseCase(intent.paletteKey).getOrThrow() }
            is SettingsIntent.SetSeedColor -> launch { setSeedColorUseCase(intent.seedColor).getOrThrow() }
            is SettingsIntent.SetBiometricEnabled -> launch { setBiometricEnabledUseCase(intent.enabled).getOrThrow() }
            is SettingsIntent.SetDoNotDisturbEnabled -> {
                notifyManager.setDoNotDisturbEnabled(intent.enabled)
                reduce { it.copy(isDoNotDisturbEnabled = notifyManager.isDoNotDisturbEnabled) }
            }
            is SettingsIntent.SetScreenshotBlockEnabled -> {
                launch { setScreenshotBlockEnabledUseCase(intent.enabled).getOrThrow() }
            }
            is SettingsIntent.SetUpdateAvailable -> {
                reduce { it.copy(isUpdateAvailable = intent.state) }
            }
            is SettingsIntent.SetGrammaticalGender -> {
                uiInteractor.setGrammaticalGender(intent.value)
                reduce { it.copy(grammaticalGender = GrammaticalGender.transform(intent.value)) }
            }
            is SettingsIntent.SetAppIcon -> {
                uiInteractor.setIcon(intent.icon)
                reduce { it.copy(enabledIcon = intent.icon) }
            }
            is SettingsIntent.ResetSettings -> {
                launch {
                    resetSettingsUseCase(Unit).getOrThrow()
                    interactor.resetLanguage()
                    uiInteractor.setGrammaticalGender(GrammaticalGender.NotSpecified().value)
                    uiInteractor.setIcon(IconAlias.Red)
                    reduce { it.copy(
                        grammaticalGender = GrammaticalGender.NotSpecified(),
                        enabledIcon = uiInteractor.enabledIcon)
                    }
                }
            }
        }
    }
}
