package org.michaelbel.movies.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.placeholder.PlaceholderHighlight
import org.michaelbel.movies.ui.placeholder.material3.fade
import org.michaelbel.movies.ui.placeholder.placeholder
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsContent(
    movie: MoviePojo,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isThemeAmoled: Boolean = false,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholder: Boolean = false
) {
    val scrollState = rememberScrollState()
    var isNoImageVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(movie.backdropPath.formatBackdropImage)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(MoviesContentDescriptionCommon.MovieDetailsImage),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(220.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inversePrimary)
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                ),
            onState = { state ->
                isNoImageVisible = movie.isNotEmpty && (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Empty)
            },
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = MaterialTheme.typography.titleLarge.copy(onContainerColor)
        )

        Text(
            text = movie.overview,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                ),
            style = MaterialTheme.typography.bodyMedium.copy(onContainerColor)
        )
    }
}

@Preview
@Composable
private fun DetailsContentPreview(
    /*@PreviewParameter(MovieDbPreviewParameterProvider::class)*/ movie: MoviePojo
) {
    MoviesTheme {
        DetailsContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            movie = movie,
            onNavigateToGallery = {}
        )
    }
}