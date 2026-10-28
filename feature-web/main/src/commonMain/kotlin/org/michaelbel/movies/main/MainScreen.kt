@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.koinConfiguration
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.detailsweb.DetailsWebScreen
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.main.tabs.MainWebTabsScreen
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.ui.theme.AppTheme

@Composable
fun MainScreen() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(interactorKoinModule)
        }
    ) {
        MainScreenContent()
    }
}

@Composable
private fun MainScreenContent() {
    val interactor: Interactor = koinInject()
    val themeData by interactor.themeData.collectAsState(initial = ThemeData.Default)

    var tab by remember { mutableStateOf<MainWebDestination>(MainWebDestination.Feed) }
    var details by remember { mutableStateOf<MainWebDestination.Details?>(null) }

    AppTheme(
        themeData = themeData
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            MainWebTabsScreen(
                currentDestination = tab,
                onDestinationChange = { tab = it },
                onMovieClick = { pagingKey, movieId ->
                    details = MainWebDestination.Details(
                        pagingKey = pagingKey,
                        movieId = movieId
                    )
                }
            )

            val currentDetails = details
            if (currentDetails != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {}
                ) {
                    DetailsWebScreen(
                        pagingKey = currentDetails.pagingKey,
                        movieId = currentDetails.movieId,
                        onBackClick = { details = null }
                    )
                }
            }
        }
    }
}



sealed interface MainWebDestination {
    data object Feed: MainWebDestination
    data object Settings: MainWebDestination
    data class Details(
        val pagingKey: PagingKey,
        val movieId: MovieId
    ): MainWebDestination
}

val mainWebWindowWidth: Dp
    @Composable get() = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp() }
