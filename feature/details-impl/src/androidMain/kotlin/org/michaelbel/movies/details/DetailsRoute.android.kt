@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package org.michaelbel.movies.details

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.details.intent.DetailsIntent
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.onPrimaryContainer
import org.michaelbel.movies.details.ktx.primaryContainer
import org.michaelbel.movies.details.ktx.scrolledContainerColor
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.details.model.DetailsModel
import org.michaelbel.movies.details.ui.DetailsContent
import org.michaelbel.movies.details.ui.DetailsFailure
import org.michaelbel.movies.details.ui.DetailsLoading
import org.michaelbel.movies.details.ui.DetailsToolbar
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.network.connectivity.ktx.isAvailable
import org.michaelbel.movies.network.ktx.isFailure
import org.michaelbel.movies.network.ktx.throwable
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.navigateToShareText
import org.michaelbel.movies.ui.ktx.screenHeight
import org.michaelbel.movies.ui.ktx.screenWidth
import org.michaelbel.movies.ui.strings.MoviesStrings
import java.net.UnknownHostException

@Composable
actual fun DetailsScreen(
    destination: org.michaelbel.movies.ui.navigation.DetailsDestination,
    viewModel: DetailsViewModel
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    val context = LocalContext.current
    val shareTitle = stringResource(MoviesStrings.share_via)

    DetailsScreenContent(
        state = state,
        dispatch = viewModel::dispatch,
        onShareClick = { context.navigateToShareText(it, shareTitle) }
    )
}

@Composable
private fun DetailsScreenContent(
    state: DetailsModel,
    dispatch: (DetailsIntent) -> Unit,
    onShareClick: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (state.networkStatus.isAvailable && state.detailsState.isFailure && state.detailsState.throwable is UnknownHostException) {
        dispatch(DetailsIntent.LoadMovie)
    }

    val animateContainerColor = animateColorAsState(
        targetValue = state.detailsState.primaryContainer(state.appTheme is AppTheme.Amoled),
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateContainerColor"
    )
    val animateOnContainerColor = animateColorAsState(
        targetValue = state.detailsState.onPrimaryContainer(state.appTheme is AppTheme.Amoled),
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateOnContainerColor"
    )

    LazyRow(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
    ) {
        item {
            Scaffold(
                modifier = Modifier
                    .width(screenWidth)
                    .height(screenHeight)
                    .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
                topBar = {
                    DetailsToolbar(
                        movieTitle = state.detailsState.toolbarTitle,
                        movieUrl = state.detailsState.movieUrl,
                        onNavigationIconClick = { dispatch(DetailsIntent.BackClick) },
                        onShareClick = onShareClick,
                        topAppBarScrollBehavior = topAppBarScrollBehavior,
                        onContainerColor = animateOnContainerColor.value,
                        scrolledContainerColor = state.detailsState.scrolledContainerColor(state.appTheme is AppTheme.Amoled),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = animateContainerColor.value
            ) { innerPadding ->
                when (state.detailsState) {
                    is ScreenState.Loading -> {
                        DetailsLoading(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                        )
                    }
                    is ScreenState.Content<*> -> {
                        DetailsContent(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize(),
                            movie = state.detailsState.movie,
                            onContainerColor = animateOnContainerColor.value,
                            isThemeAmoled = state.appTheme is AppTheme.Amoled,
                            onNavigateToGallery = { dispatch(DetailsIntent.GalleryClick) },
                            onGenerateColors = { movieId, containerColor, onContainerColor -> dispatch(DetailsIntent.GenerateColors(movieId, containerColor, onContainerColor)) }
                        )
                    }
                    is ScreenState.Failure -> {
                        DetailsFailure(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}