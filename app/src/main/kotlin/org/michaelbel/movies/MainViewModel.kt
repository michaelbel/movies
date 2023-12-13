package org.michaelbel.movies

import android.app.Activity
import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.common.ktx.printlnDebug
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.messaging.TokenListener
import org.michaelbel.movies.platform.update.UpdateService
import org.michaelbel.movies.work.AccountUpdateWorker
import org.michaelbel.movies.work.MoviesDatabaseWorker

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val interactor: Interactor,
    private val updateService: UpdateService,
    private val analytics: MoviesAnalytics,
    private val messagingService: MessagingService,
    private val workManager: WorkManager
): BaseViewModel() {

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
        fetchRemoteConfig()
        fetchFirebaseMessagingToken()
        prepopulateDatabase()
        updateAccountDetails()
    }

    fun analyticsTrackDestination(destination: NavDestination, arguments: Bundle?) {
        analytics.trackDestination(destination.route, arguments)
    }

    fun startUpdateFlow(activity: Activity) {
        updateService.startUpdate(activity)
    }

    private fun fetchRemoteConfig() = launch {
        interactor.fetchRemoteConfig()
    }

    private fun fetchFirebaseMessagingToken() {
        messagingService.setTokenListener(object: TokenListener {
            override fun onNewToken(token: String) {
                printlnDebug("firebase messaging token: $token")
            }
        })
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

    companion object {
        const val MOVIES_DATA_FILENAME = "movies.json"
    }
}