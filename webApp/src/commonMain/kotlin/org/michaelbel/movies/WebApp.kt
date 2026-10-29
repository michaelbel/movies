package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration
import org.michaelbel.movies.account.di.accountKoinModule
import org.michaelbel.movies.auth.di.authKoinModule
import org.michaelbel.movies.debug.di.debugKoinModule
import org.michaelbel.movies.details.di.detailsKoinModule
import org.michaelbel.movies.fave.di.faveKoinModule
import org.michaelbel.movies.feed.di.feedKoinModule
import org.michaelbel.movies.gallery.di.galleryKoinModule
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.interactor.di.webInteractorKoinModule
import org.michaelbel.movies.main.MainScreen
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.main.di.mainKoinModule
import org.michaelbel.movies.main.intent.MainIntent
import org.michaelbel.movies.main.tabs.di.mainTabsKoinModule
import org.michaelbel.movies.notify.di.notifyKoinModule
import org.michaelbel.movies.platform.inject.flavorServiceKtorModule
import org.michaelbel.movies.settings.di.settingsKoinModule
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.webui.theme.WebTheme

@Composable
fun WebApp() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(
                webInteractorKoinModule,
                flavorServiceKtorModule,
                mainKoinModule,
                mainTabsKoinModule,
                accountKoinModule,
                authKoinModule,
                debugKoinModule,
                detailsKoinModule,
                faveKoinModule,
                feedKoinModule,
                galleryKoinModule,
                notifyKoinModule,
                settingsKoinModule
            )
        }
    ) {
        WebAppContent()
    }
}

@Composable
private fun WebAppContent(
    browserInteractor: BrowserInteractor = koinInject(),
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    LaunchedEffect(browserInteractor) {
        val redirect = browserInteractor.consumeAuthRedirect()
        val requestToken = redirect?.requestToken
        val approved = redirect?.approved
        when {
            requestToken == null || approved == null -> Unit
            else -> {
                viewModel.dispatch(
                    MainIntent.NavigateToMain(
                        requestToken = requestToken,
                        approved = approved
                    )
                )
            }
        }
    }

    WebTheme(
        themeData = state.themeData
    ) {
        MainScreen(
            viewModel = viewModel
        )
    }
}
