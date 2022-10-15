package org.michaelbel.movies.details.ui

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
        }
    )
}

private class TitlePreviewParameterProvider: PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf(
        "How to Train Your Dragon",
        "Three Billboards Outside Ebbing, Missouri"
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