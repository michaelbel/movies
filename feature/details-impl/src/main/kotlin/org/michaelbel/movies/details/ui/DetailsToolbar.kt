package org.michaelbel.movies.details.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.details.ui.preview.TitlePreviewParameterProvider
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit,
    movieTitle: String
) {
    TopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavigationIconClick()
                }
            ) {
                Icon(
                    imageVector = MoviesIcons.ArrowBack,
                    contentDescription = null
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
            movieTitle = title
        )
    }
}