@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package org.michaelbel.movies.details.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.palette.graphics.Palette
import java.net.UnknownHostException
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.onPrimaryContainer
import org.michaelbel.movies.details.ktx.primaryContainer
import org.michaelbel.movies.details.ktx.scrolledContainerColor
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.connectivity.ktx.isAvailable
import org.michaelbel.movies.network.ktx.isFailure
import org.michaelbel.movies.network.ktx.throwable
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.screenHeight
import org.michaelbel.movies.ui.ktx.screenWidth

@Composable
internal fun DetailsScreenContent(
    onBackClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    onGenerateColors: (Int, Palette) -> Unit,
    detailsState: ScreenState,
    networkStatus: NetworkStatus,
    isThemeAmoled: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (networkStatus.isAvailable && detailsState.isFailure && detailsState.throwable is UnknownHostException) {
        onRetry()
    }

    val animateContainerColor = animateColorAsState(
        targetValue = detailsState.primaryContainer(isThemeAmoled),
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateContainerColor"
    )
    val animateOnContainerColor = animateColorAsState(
        targetValue = detailsState.onPrimaryContainer(isThemeAmoled),
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateOnContainerColor"
    )

    LazyRow(
        modifier = modifier.fillMaxSize(),
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
                        movieTitle = detailsState.toolbarTitle,
                        movieUrl = detailsState.movieUrl,
                        onNavigationIconClick = onBackClick,
                        onShareClick = onShareClick,
                        topAppBarScrollBehavior = topAppBarScrollBehavior,
                        onContainerColor = animateOnContainerColor.value,
                        scrolledContainerColor = detailsState.scrolledContainerColor(isThemeAmoled),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                containerColor = animateContainerColor.value
            ) { innerPadding ->
                when (detailsState) {
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
                            movie = detailsState.movie,
                            onContainerColor = animateOnContainerColor.value,
                            isThemeAmoled = isThemeAmoled,
                            onNavigateToGallery = onNavigateToGallery,
                            onGenerateColors = onGenerateColors
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