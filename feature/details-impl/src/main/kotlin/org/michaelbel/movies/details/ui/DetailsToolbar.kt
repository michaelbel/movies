package org.michaelbel.movies.details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.ShareIcon
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.TitlePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DetailsToolbar(
    movieTitle: String,
    movieUrl: String?,
    onNavigationIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
                        url = movieUrl
                    )
                }
            }
        },
        navigationIcon = {
            BackIcon(
                onClick = onNavigationIconClick,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
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