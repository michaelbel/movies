@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.network.config.formatPosterImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.placeholder.PlaceholderHighlight
import org.michaelbel.movies.ui.placeholder.material3.fade
import org.michaelbel.movies.ui.placeholder.placeholder
import org.michaelbel.movies.ui.preview.MovieDbPreviewParameterProvider
import org.michaelbel.movies.ui.preview.wrapper.ThemeWrapper

@Composable
fun DetailsContent(
    movie: MoviePojo,
    onNavigateToGallery: () -> Unit = {},
    isDetailsGalleryFeatureEnabled: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(),
    placeholder: Boolean = false,
    isWide: Boolean = false
) {
    val platformContext = LocalPlatformContext.current
    var isNoImageVisible by remember { mutableStateOf(false) }

    when {
        isWide -> {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ElevatedCard(
                    shape = MaterialTheme.shapes.largeIncreased,
                    modifier = Modifier
                        .width(320.dp)
                        .verticalScroll(rememberScrollState())
                        .clip(MaterialTheme.shapes.largeIncreased)
                        .clickable(
                            enabled = isDetailsGalleryFeatureEnabled && !placeholder && !isNoImageVisible,
                            onClick = onNavigateToGallery
                        )
                ) {
                    val imageRequest: ImageRequest? = if (placeholder) {
                        null
                    } else {
                        ImageRequest.Builder(platformContext)
                            .data(movie.posterPath.formatPosterImage)
                            .crossfade(true)
                            .build()
                    }

                    AsyncImage(
                        model = imageRequest,
                        contentDescription = stringResource(MoviesContentDescription.MovieDetailsImage),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2F / 3F)
                            .clip(MaterialTheme.shapes.largeIncreased)
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .placeholder(
                                visible = placeholder,
                                color = MaterialTheme.colorScheme.inversePrimary,
                                shape = MaterialTheme.shapes.largeIncreased,
                                highlight = PlaceholderHighlight.fade()
                            ),
                        onState = { state ->
                            isNoImageVisible = movie.isNotEmpty && (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty)
                        },
                        contentScale = ContentScale.Crop
                    )
                }

                SelectionContainer(
                    modifier = Modifier
                        .weight(1F)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = movie.overview,
                        modifier = Modifier
                            .widthIn(max = 720.dp)
                            .placeholder(
                                visible = placeholder,
                                color = MaterialTheme.colorScheme.inversePrimary,
                                shape = MaterialTheme.shapes.large,
                                highlight = PlaceholderHighlight.fade()
                            ),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }
        else -> {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = contentPadding + PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        ElevatedCard(
                            shape = MaterialTheme.shapes.largeIncreased,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.largeIncreased)
                                .clickable(
                                    enabled = isDetailsGalleryFeatureEnabled && !placeholder && !isNoImageVisible,
                                    onClick = onNavigateToGallery
                                )
                        ) {
                            val imageRequest: ImageRequest? = if (placeholder) {
                                null
                            } else {
                                ImageRequest.Builder(platformContext)
                                    .data(movie.backdropPath.formatBackdropImage)
                                    .crossfade(true)
                                    .build()
                            }

                            AsyncImage(
                                model = imageRequest,
                                contentDescription = stringResource(MoviesContentDescription.MovieDetailsImage),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(minOf((this@BoxWithConstraints.maxWidth - 32.dp) * 9F / 16F, this@BoxWithConstraints.maxHeight * 0.4F))
                                    .clip(MaterialTheme.shapes.largeIncreased)
                                    .background(MaterialTheme.colorScheme.inversePrimary)
                                    .placeholder(
                                        visible = placeholder,
                                        color = MaterialTheme.colorScheme.inversePrimary,
                                        shape = MaterialTheme.shapes.largeIncreased,
                                        highlight = PlaceholderHighlight.fade()
                                    ),
                                onState = { state ->
                                    isNoImageVisible = movie.isNotEmpty && (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty)
                                },
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    item {
                        SelectionContainer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .placeholder(
                                    visible = placeholder,
                                    color = MaterialTheme.colorScheme.inversePrimary,
                                    shape = MaterialTheme.shapes.large,
                                    highlight = PlaceholderHighlight.fade()
                                )
                        ) {
                            Text(
                                text = movie.overview,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewWrapper(ThemeWrapper::class)
@Preview(showBackground = true)
@Composable
private fun DetailsContentPreview(
    @PreviewParameter(DetailsContentPreviewParameterProvider::class) state: DetailsContentPreviewState
) {
    DetailsContent(
        movie = state.movie,
        isWide = state.isWide
    )
}

private data class DetailsContentPreviewState(
    val movie: MoviePojo,
    val isWide: Boolean
)

private class DetailsContentPreviewParameterProvider: PreviewParameterProvider<DetailsContentPreviewState> {
    private val movies: List<MoviePojo> = MovieDbPreviewParameterProvider().values.toList()

    override val values: Sequence<DetailsContentPreviewState>
        get() = sequence {
            movies.forEach { movie ->
                yield(
                    DetailsContentPreviewState(
                        movie = movie,
                        isWide = false
                    )
                )
            }
            yield(
                DetailsContentPreviewState(
                    movie = movies.first(),
                    isWide = true
                )
            )
        }
}
