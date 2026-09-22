package org.michaelbel.movies

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.michaelbel.movies.main.MainScreen
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.main.intent.MainIntent
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.resolveNotificationPreferencesIntent
import org.michaelbel.movies.ui.setScreenshotBlockEnabled
import org.michaelbel.movies.ui.supportRegisterScreenCaptureCallback
import org.michaelbel.movies.ui.supportUnregisterScreenCaptureCallback
import org.michaelbel.movies.ui.navigation.INTENT_ACTION_SEARCH
import org.michaelbel.movies.ui.navigation.INTENT_ACTION_SETTINGS
import org.michaelbel.movies.ui.shortcuts.installShortcuts
import org.michaelbel.movies.ui.theme.AppTheme

class MainActivity: FragmentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val screenCaptureCallback: Any by lazy {
        if (Build.VERSION.SDK_INT >= 34) {
            ScreenCaptureCallback {}
        } else {
            Unit
        }
    }

    private val Uri.tmdbMovieId: Int?
        get() {
            return when (host) {
                "www.themoviedb.org" -> when (pathSegments.firstOrNull()) {
                    "movie" -> pathSegments.getOrNull(1)?.takeWhile(Char::isDigit)?.toIntOrNull()
                    else -> null
                }
                else -> null
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.stateFlow.value.splashLoading } }
        super.onCreate(savedInstanceState)
        installShortcuts()
        setContent {
            val state by viewModel.stateFlow.collectAsStateCommon()

            AppTheme(
                themeData = state.themeData,
                enableEdgeToEdge = { statusBarStyle, navigationBarStyle ->
                    enableEdgeToEdge(statusBarStyle as SystemBarStyle, navigationBarStyle as SystemBarStyle)
                }
            ) {
                MainScreen(
                    onFinish = ::finish,
                    onAuthenticate = { viewModel.dispatch(MainIntent.Authenticate(this)) },
                    onScreenshotBlockEnabledChanged = { window.setScreenshotBlockEnabled(it) },
                    onRequestReview = { viewModel.dispatch(MainIntent.RequestReview(this)) },
                    onRequestUpdate = { viewModel.dispatch(MainIntent.RequestUpdate(this)) },
                    viewModel = viewModel
                )
            }
        }
        resolveNotificationPreferencesIntent()
        resolveIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        supportRegisterScreenCaptureCallback(screenCaptureCallback)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        resolveIntent(intent)
    }

    override fun onStop() {
        super.onStop()
        supportUnregisterScreenCaptureCallback(screenCaptureCallback)
    }

    private fun resolveIntent(intent: Intent?) {
        val uri = intent?.data
        val tmdbMovieId = uri?.tmdbMovieId
        when {
            intent?.dataString == INTENT_ACTION_SEARCH -> viewModel.dispatch(MainIntent.ShortcutSearchClick)
            intent?.dataString == INTENT_ACTION_SETTINGS -> viewModel.dispatch(MainIntent.ShortcutSettingsClick)
            uri?.scheme == "movies" && uri.host == "redirect_url" -> handleRedirectUrl(uri)
            uri?.scheme == "movies" && uri.host == "details" -> handleDetailsDeepLink(uri)
            tmdbMovieId != null -> viewModel.dispatch(MainIntent.NavigateToDetails(tmdbMovieId))
        }
    }

    private fun handleRedirectUrl(uri: Uri) {
        val requestToken = uri.getQueryParameter("request_token")?.takeIf(String::isNotBlank)
        val approved = parseApproved(uri.getQueryParameter("approved"))
        if (requestToken != null && approved != null) {
            viewModel.dispatch(MainIntent.NavigateToMain(requestToken, approved))
        }
    }

    private fun parseApproved(value: String?): Boolean? {
        return when (value?.lowercase()) {
            "true", "1" -> true
            "false", "0" -> false
            else -> null
        }
    }

    private fun handleDetailsDeepLink(uri: Uri) {
        val movieId = uri.pathSegments.firstOrNull()?.toIntOrNull()
        movieId?.let { viewModel.dispatch(MainIntent.NavigateToDetails(it)) }
    }
}
