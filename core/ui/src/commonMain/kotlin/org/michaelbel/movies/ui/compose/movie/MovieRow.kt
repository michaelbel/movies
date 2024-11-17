package org.michaelbel.movies.ui.compose.movie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.ktx.isErrorOrEmpty
import org.michaelbel.movies.ui.preview.MoviePreviewParameterProvider
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun MovieRow(
    movie: MoviePojo,
    modifier: Modifier = Modifier,
    maxLines: Int = 10
) {
    var isNoImageVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        if (movie.isNotEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalPlatformContext.current)
                        .data(movie.backdropPath.formatBackdropImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = MoviesContentDescriptionCommon.None,
                    modifier = Modifier.fillMaxSize(),
                    onState = { state ->
                        isNoImageVisible = state.isErrorOrEmpty
                    },
                    contentScale = ContentScale.Crop
                )

                this@Column.AnimatedVisibility(
                    visible = isNoImageVisible,
                    enter = fadeIn(),
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(MoviesStrings.no_image),
                        style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)
                    )
                }
            }

            Text(
                text = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Preview
@Composable
private fun MovieRowPreview(
    @PreviewParameter(MoviePreviewParameterProvider::class) movie: MoviePojo
) {
    MoviesTheme {
        MovieRow(
            movie = movie,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inversePrimary)
        )
    }
}