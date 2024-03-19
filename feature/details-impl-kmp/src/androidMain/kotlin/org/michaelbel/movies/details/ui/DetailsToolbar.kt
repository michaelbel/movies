@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.ShareIcon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.TitlePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsToolbar(
    movieTitle: String,
    movieUrl: String?,
    onNavigationIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    scrolledContainerColor: Color = MaterialTheme.colorScheme.inversePrimary
) {
    TopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(onContainerColor)
            )
        },
        modifier = modifier,
        actions = {
            AnimatedVisibility(
                visible = movieUrl != null,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                enter = fadeIn()
            ) {
                if (movieUrl != null) {
                    ShareIcon(
                        url = movieUrl,
                        onContainerColor = onContainerColor
                    )
                }
            }
        },
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                onContainerColor = onContainerColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = scrolledContainerColor
        ),
        scrollBehavior = topAppBarScrollBehavior
    )
}

@Composable
@DevicePreviews
private fun DetailsToolbarPreview(
    @PreviewParameter(TitlePreviewParameterProvider::class) title: String
) {
    MoviesTheme {
        DetailsToolbar(
            movieTitle = title,
            movieUrl = null,
            onNavigationIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Composable
@Preview
private fun DetailsToolbarAmoledPreview(
    @PreviewParameter(TitlePreviewParameterProvider::class) title: String
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        DetailsToolbar(
            movieTitle = title,
            movieUrl = null,
            onNavigationIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}