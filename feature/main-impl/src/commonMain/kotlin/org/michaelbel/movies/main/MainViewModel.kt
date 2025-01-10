package org.michaelbel.movies.main

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.biometric.BiometricInteractor
import org.michaelbel.movies.common.biometric.BiometricListener
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.debug.DebugNotificationInteractor
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.platform.config.ConfigService
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.review.ReviewService
import org.michaelbel.movies.platform.update.UpdateService
import org.michaelbel.movies.ui.ktx.isDebug
import org.michaelbel.movies.work.WorkManagerInteractor

class MainViewModel(
    private val interactor: Interactor,
    private val biometricController: BiometricInteractor,
    private val analytics: MoviesAnalytics,
    private val messagingService: MessagingService,
    private val workManagerInteractor: WorkManagerInteractor,
    private val debugNotificationInteractor: DebugNotificationInteractor,
    private val configService: ConfigService,
    private val reviewService: ReviewService,
    private val updateService: UpdateService,
): BaseViewModel() {

    private val _authenticateFlow = Channel<Unit>()
    val authenticateFlow: Flow<Unit> get() = _authenticateFlow.receiveAsFlow()

    private val _cancelFlow = Channel<Unit>()
    val cancelFlow: Flow<Unit> get() = _cancelFlow.receiveAsFlow()

    private val _splashLoading = MutableStateFlow(true)
    val splashLoading: StateFlow<Boolean> get() = _splashLoading.asStateFlow()

    val themeData: StateFlow<ThemeData> = interactor.themeData
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = ThemeData.Default
        )

    val isScreenshotBlockEnabled: StateFlow<Boolean> = interactor.isScreenshotBlockEnabled
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    init {
        fetchBiometric()
        fetchRemoteConfig()
        fetchFirebaseMessagingToken()
        prepopulateDatabase()
        updateAccountDetails()
        showDebugNotification()
    }

    /*fun analyticsTrackDestination(destination: NavDestination, arguments: Bundle?) {
        val hashMap = hashMapOf<String, String>()
        arguments?.keySet()?.forEach { key ->
            hashMap[key] = arguments.getString(key).orEmpty()
        }
        analytics.trackDestination(destination.route, hashMap)
    }*/

    fun authenticate(activity: Any) {
        val biometricListener = object: BiometricListener {
            override fun onSuccess() {
                _splashLoading.value = false
            }

            override fun onCancel() {
                scope.launch { _cancelFlow.send(Unit) }
            }
        }
        biometricController.authenticate(activity, biometricListener)
    }

    fun requestReview(activity: Any) {
        reviewService.requestReview(activity)
    }

    fun requestUpdate(activity: Any) {
        updateService.startUpdate(activity)
    }

    private fun fetchBiometric() = scope.launch {
        val isBiometricEnabled = interactor.isBiometricEnabledAsync()
        _splashLoading.value = isBiometricEnabled
        if (isBiometricEnabled) {
            _authenticateFlow.send(Unit)
        }
    }

    private fun fetchRemoteConfig() = scope.launch {
        configService.fetchAndActivate()
    }

    private fun fetchFirebaseMessagingToken() {
        /*messagingService.setTokenListener(object: TokenListener {
            override fun onNewToken(token: String) {
                printlnDebug("firebase messaging token: $token")
            }
        })*/
    }

    private fun prepopulateDatabase() {
        workManagerInteractor.prepopulateDatabase()
    }

    private fun updateAccountDetails() {
        workManagerInteractor.updateAccountDetails()
    }

    private fun showDebugNotification() {
        if (isDebug) {
            debugNotificationInteractor.showDebugNotification()
        }
    }
}