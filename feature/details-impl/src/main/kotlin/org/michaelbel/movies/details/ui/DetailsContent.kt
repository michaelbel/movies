package org.michaelbel.movies.details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.network.formatBackdropImage
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.isNotEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.ktx.context
import org.michaelbel.movies.ui.ktx.isErrorOrEmpty
import org.michaelbel.movies.ui.placeholder.PlaceholderHighlight
import org.michaelbel.movies.ui.placeholder.material3.fade
import org.michaelbel.movies.ui.placeholder.placeholder
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MovieDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.AmoledTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DetailsContent(
    movie: MovieDb,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: Boolean = false
) {
    val scrollState = rememberScrollState()
    var isNoImageVisible: Boolean by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        val (image, noImageText, title, overview) = createRefs()

        val imageRequest: ImageRequest? = if (placeholder) {
            null
        } else {
            ImageRequest.Builder(context)
                .data(movie.backdropPath.formatBackdropImage)
                .crossfade(true)
                .build()
        }

        AsyncImage(
            model = imageRequest,
            contentDescription = stringResource(MoviesContentDescription.MovieDetailsImage),
            modifier = Modifier
                .constrainAs(image) {
                    width = Dimension.fillToConstraints
                    height = Dimension.value(220.dp)
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .clip(MaterialTheme.shapes.small)
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                )
                .clickable(
                    enabled = !placeholder && !isNoImageVisible,
                    onClick = { onNavigateToGallery(movie.movieId) }
                ) ,
            onState = { state ->
                isNoImageVisible = movie.isNotEmpty && state.isErrorOrEmpty
            },
            contentScale = ContentScale.Crop
        )

        AnimatedVisibility(
            visible = isNoImageVisible,
            modifier = Modifier.constrainAs(noImageText) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(title.top)
            },
            enter = fadeIn()
        ) {
            Text(
                text = stringResource(R.string.details_no_image),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }

        Text(
            text = movie.title,
            modifier = Modifier
                .constrainAs(title) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(image.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        Text(
            text = movie.overview,
            modifier = Modifier
                .constrainAs(overview) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(title.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .placeholder(
                    visible = placeholder,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    shape = MaterialTheme.shapes.small,
                    highlight = PlaceholderHighlight.fade()
                ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 10,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
    }
}

@Composable
@DevicePreviews
private fun DetailsContentPreview(
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
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

@Composable
@Preview
private fun DetailsContentAmoledPreview(
    @PreviewParameter(MovieDbPreviewParameterProvider::class) movie: MovieDb
) {
    AmoledTheme {
        DetailsContent(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            movie = movie,
            onNavigateToGallery = {}
        )
    }
}