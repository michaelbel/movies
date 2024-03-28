package org.michaelbel.movies

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDestination
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
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
import org.michaelbel.movies.app.BuildConfig
import org.michaelbel.movies.common.biometric.BiometricController
import org.michaelbel.movies.common.biometric.BiometricListener
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.debug.notification.DebugNotificationClient
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.platform.config.ConfigService
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.work.AccountUpdateWorker
import org.michaelbel.movies.work.MoviesDatabaseWorker

internal class MainViewModel(
    private val interactor: Interactor,
    private val biometricController: BiometricController,
    private val analytics: MoviesAnalytics,
    private val messagingService: MessagingService,
    private val workManager: WorkManager,
    private val debugNotificationClient: DebugNotificationClient,
    private val configService: ConfigService
): BaseViewModel() {

    private val _authenticateFlow = Channel<Unit>(Channel.BUFFERED)
    val authenticateFlow: Flow<Unit> = _authenticateFlow.receiveAsFlow()

    private val _cancelFlow = Channel<Unit>(Channel.BUFFERED)
    val cancelFlow: Flow<Unit> = _cancelFlow.receiveAsFlow()

    private val _splashLoading = MutableStateFlow(true)
    val splashLoading: StateFlow<Boolean> = _splashLoading.asStateFlow()

    val currentTheme: StateFlow<AppTheme> = interactor.currentTheme
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = interactor.dynamicColors
        .stateIn(
            scope = this,
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

    fun analyticsTrackDestination(destination: NavDestination, arguments: Bundle?) {
        val hashMap = hashMapOf<String, String>()
        arguments?.keySet()?.forEach { key ->
            hashMap[key] = arguments.get(key).toString()
        }
        analytics.trackDestination(destination.route, hashMap)
    }

    fun authenticate(activity: FragmentActivity) {
        val biometricListener = object: BiometricListener {
            override fun onSuccess() {
                _splashLoading.value = false
            }

            override fun onCancel() {
                launch { _cancelFlow.send(Unit) }
            }
        }
        biometricController.authenticate(activity, biometricListener)
    }

    private fun fetchBiometric() = launch {
        val isBiometricEnabled = interactor.isBiometricEnabledAsync()
        _splashLoading.value = isBiometricEnabled
        if (isBiometricEnabled) {
            _authenticateFlow.send(Unit)
        }
    }

    private fun fetchRemoteConfig() = launch {
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
        val request = OneTimeWorkRequestBuilder<MoviesDatabaseWorker>()
            .setInputData(workDataOf(MoviesDatabaseWorker.KEY_FILENAME to MOVIES_DATA_FILENAME))
            .build()
        workManager.enqueue(request)
    }

    private fun updateAccountDetails() {
        val request = OneTimeWorkRequestBuilder<AccountUpdateWorker>()
            .build()
        workManager.enqueue(request)
    }

    private fun showDebugNotification() {
        if (BuildConfig.DEBUG) {
            debugNotificationClient.showDebugNotification()
        }
    }

    companion object {
        const val MOVIES_DATA_FILENAME = "movies.json"
    }
}