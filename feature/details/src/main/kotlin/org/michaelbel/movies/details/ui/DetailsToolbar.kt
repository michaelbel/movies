package org.michaelbel.movies.details.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import org.michaelbel.movies.ui.icon.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit,
    movieTitle: String
) {
    SmallTopAppBar(
        title = {
            Text(
                text = movieTitle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
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
        }
    )
}

@Composable
@DevicePreviews
private fun DetailsToolbarPreview() {
    MoviesTheme {
        DetailsToolbar(
            modifier = Modifier
                .statusBarsPadding(),
            onNavigationIconClick = {},
            movieTitle = "How to train your dragon"
        )
    }
}