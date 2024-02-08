package org.michaelbel.movies.details.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import java.net.UnknownHostException
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.connectivity.ktx.isAvailable
import org.michaelbel.movies.network.ktx.isFailure
import org.michaelbel.movies.network.ktx.throwable
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MovieDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState by viewModel.detailsState.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
    val containerColor = viewModel.containerColor
    val onContainerColor = viewModel.onContainerColor

    DetailsScreenContent(
        onBackClick = onBackClick,
        onNavigateToGallery = onNavigateToGallery,
        onGenerateColors = viewModel::onGenerateColors,
        detailsState = detailsState,
        networkStatus = networkStatus,
        containerColor = containerColor,
        onContainerColor = onContainerColor,
        isThemeAmoled = currentTheme is AppTheme.Amoled,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}

@Composable
private fun DetailsScreenContent(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    onGenerateColors: (Palette) -> Unit,
    detailsState: ScreenState,
    networkStatus: NetworkStatus,
    containerColor: Int?,
    onContainerColor: Int?,
    isThemeAmoled: Boolean,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (networkStatus.isAvailable && detailsState.isFailure && detailsState.throwable is UnknownHostException) {
        onRetry()
    }

    val animateContainerColor = animateColorAsState(
        targetValue = if (containerColor != null) Color(containerColor) else MaterialTheme.colorScheme.primaryContainer,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateContainerColor"
    )
    val animateOnContainerColor = animateColorAsState(
        targetValue = if (onContainerColor != null) Color(onContainerColor) else MaterialTheme.colorScheme.onPrimaryContainer,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 0,
            easing = LinearEasing
        ),
        label = "animateOnContainerColor"
    )

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            DetailsToolbar(
                movieTitle = detailsState.toolbarTitle,
                movieUrl = detailsState.movieUrl,
                onNavigationIconClick = onBackClick,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                onContainerColor = animateOnContainerColor.value,
                scrolledContainerColor = if (containerColor != null) Color(containerColor) else MaterialTheme.colorScheme.inversePrimary,
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

@Composable
@DevicePreviews
private fun DetailsScreenContentPreview(
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme {
        DetailsScreenContent(
            onBackClick = {},
            onNavigateToGallery = {},
            onGenerateColors = {},
            detailsState = ScreenState.Content(movie),
            networkStatus = NetworkStatus.Available,
            containerColor = null,
            onContainerColor = null,
            isThemeAmoled = false,
            onRetry = {},
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun DetailsScreenContentAmoledPreview(
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        DetailsScreenContent(
            onBackClick = {},
            onNavigateToGallery = {},
            onGenerateColors = {},
            detailsState = ScreenState.Content(movie),
            networkStatus = NetworkStatus.Available,
            containerColor = null,
            onContainerColor = null,
            isThemeAmoled = false,
            onRetry = {},
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}