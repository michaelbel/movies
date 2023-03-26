package org.michaelbel.movies.details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.TitlePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DetailsToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit,
    movieTitle: String,
    movieUrl: String?
) {
    TopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier,
        actions = {
            AnimatedVisibility(
                visible = movieUrl != null,
                enter = fadeIn()
            ) {
                if (movieUrl != null) {
                    DetailsShareButton(
                        movieUrl = movieUrl
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationIconClick()
                }
            ) {
                Image(
                    imageVector = MoviesIcons.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
@DevicePreviews
private fun DetailsToolbarPreview(
    @PreviewParameter(TitlePreviewParameterProvider::class) title: String
) {
    MoviesTheme {
        DetailsToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            onNavigationIconClick = {},
            movieTitle = title,
            movieUrl = null
        )
    }
}