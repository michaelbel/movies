package org.michaelbel.movies.main

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.domain.usecase.IsBiometricEnabledUseCase
import org.michaelbel.movies.domain.usecase.ScreenshotBlockEnabledFlowUseCase
import org.michaelbel.movies.domain.usecase.ThemeDataFlowUseCase
import org.michaelbel.movies.feed.event.FeedEvent
import org.michaelbel.movies.feed.event.FeedEventManager
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.main.event.MainEvent
import org.michaelbel.movies.main.intent.MainIntent
import org.michaelbel.movies.main.model.MainModel
import org.michaelbel.movies.main.tabs.event.MainTabsEventManager
import org.michaelbel.movies.platform.config.ConfigService
import org.michaelbel.movies.platform.review.ReviewService
import org.michaelbel.movies.platform.update.UpdateService
import org.michaelbel.movies.ui.navigation.DetailsDestination
import org.michaelbel.movies.ui.navigation.MainDestination
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.work.WorkManagerInteractor

class MainViewModel(
    private val uiInteractor: UiInteractor,
    private val workManagerInteractor: WorkManagerInteractor,
    private val configService: ConfigService,
    private val reviewService: ReviewService,
    private val updateService: UpdateService,
    private val themeDataFlowUseCase: ThemeDataFlowUseCase,
    private val screenshotBlockEnabledFlowUseCase: ScreenshotBlockEnabledFlowUseCase,
    private val isBiometricEnabledUseCase: IsBiometricEnabledUseCase
): MoviesViewModel<MainModel, MainIntent, MainEvent>(MainModel()) {

    init {
        dispatch(MainIntent.CollectThemeData)
        dispatch(MainIntent.CollectScreenshotBlockEnabled)
        dispatch(MainIntent.FetchBiometric)
        dispatch(MainIntent.FetchRemoteConfig)
        dispatch(MainIntent.FetchFirebaseMessagingToken)
        dispatch(MainIntent.PrepopulateDatabase)
        dispatch(MainIntent.UpdateAccountDetails)
    }

    override fun dispatch(intent: MainIntent) {
        when (intent) {
            is MainIntent.OpenFeed -> launch { MainTabsEventManager.push(MainEvent.OpenFeed) }
            is MainIntent.OpenSettings -> launch { MainTabsEventManager.push(MainEvent.OpenSettings) }
            is MainIntent.CollectThemeData -> {
                launch {
                    themeDataFlowUseCase(uiInteractor.defaultDynamicColorsEnabled).collectLatest { data ->
                        reduce { it.copy(themeData = data) }
                    }
                }
            }
            is MainIntent.CollectScreenshotBlockEnabled -> {
                launch {
                    screenshotBlockEnabledFlowUseCase(Unit).collectLatest { isScreenshotBlockEnabled ->
                        reduce { it.copy(isScreenshotBlockEnabled = isScreenshotBlockEnabled) }
                    }
                }
            }
            is MainIntent.RequestReview -> reviewService.requestReview(intent.activity)
            is MainIntent.RequestUpdate -> updateService.startUpdate(intent.activity)
            is MainIntent.FetchBiometric -> {
                launch {
                    val isBiometricEnabled = isBiometricEnabledUseCase(Unit).getOrThrow()
                    reduce { it.copy(splashLoading = isBiometricEnabled) }
                    if (isBiometricEnabled) {
                        push(MainEvent.BiometricAuthenticate)
                    }
                }
            }
            is MainIntent.FetchRemoteConfig -> launch { configService.fetchAndActivate() }
            is MainIntent.FetchFirebaseMessagingToken -> {
                /*messagingService.setTokenListener(object: TokenListener {
                    override fun onNewToken(token: String) {
                        printlnDebug("firebase messaging token: $token")
                    }
                })*/
            }
            is MainIntent.PrepopulateDatabase -> workManagerInteractor.prepopulateDatabase()
            is MainIntent.UpdateAccountDetails -> workManagerInteractor.updateAccountDetails()
            is MainIntent.BiometricSuccess -> reduce { it.copy(splashLoading = false) }
            is MainIntent.BiometricCancel -> launch { push(MainEvent.BiometricCancel) }
            is MainIntent.NavigateToDetails -> {
                launch {
                    MainNavigator.forward(
                        DetailsDestination(movieList = null, movieId = intent.movieId)
                    )
                }
            }
            is MainIntent.NavigateToMain -> {
                launch {
                    MainNavigator.back()
                    MainNavigator.forward(MainDestination(intent.requestToken, intent.approved))
                }
            }
            is MainIntent.ShortcutSearchClick -> {
                launch {
                    MainNavigator.forward(MainDestination(openSearch = true))
                    dispatch(MainIntent.OpenFeed)
                    FeedEventManager.push(FeedEvent.OpenSearch)
                }
            }
            is MainIntent.ShortcutSettingsClick -> {
                launch {
                    MainNavigator.forward(MainDestination())
                    dispatch(MainIntent.OpenSettings)
                }
            }
        }
    }
}
