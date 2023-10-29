package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.settings.ktx.listText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MovieListPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SettingsMovieListBox(
    currentMovieList: MovieList,
    onMovieListSelect: (MovieList) -> Unit,
    modifier: Modifier = Modifier,
) {
    var movieListDialog: Boolean by remember { mutableStateOf(false) }

    if (movieListDialog) {
        SettingsMovieListDialog(
            currentMovieList = currentMovieList,
            onMovieListSelect = onMovieListSelect,
            onDismissRequest = {
                movieListDialog = false
            }
        )
    }

    ConstraintLayout(
        modifier = modifier
            .clickable {
                movieListDialog = true
            }
            .testTag("ConstraintLayout")
    ) {
        val (title, value) = createRefs()

        Text(
            text = stringResource(R.string.settings_movie_list),
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("TitleText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Text(
            text = currentMovieList.listText,
            modifier = Modifier
                .constrainAs(value) {
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                    top.linkTo(parent.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom)
                }
                .testTag("ValueText"),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
@DevicePreviews
private fun SettingsMovieListBoxPreview(
    @PreviewParameter(MovieListPreviewParameterProvider::class) movieList: MovieList
) {
    MoviesTheme {
        SettingsMovieListBox(
            currentMovieList = movieList,
            onMovieListSelect = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}