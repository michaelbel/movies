package org.michaelbel.movies.ui.compose.movie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import androidx.constraintlayout.compose.atMost
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import coil3.request.crossfade
import movies.core.ui.generated.resources.Res
import movies.core.ui.generated.resources.no_image
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.network.config.formatPosterImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.ktx.isErrorOrEmpty
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun MovieColumnDesktop(
    movie: MoviePojo,
    modifier: Modifier = Modifier
) {
    var isNoImageVisible by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, noImageText, text) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(movie.posterPath.formatPosterImage)
                .crossfade(true)
                .build(),
            contentDescription = MoviesContentDescriptionCommon.None,
            modifier = Modifier.constrainAs(image) {
                width = Dimension.fillToConstraints.atLeast(400.dp).atMost(400.dp)
                height = Dimension.ratio("3:2")
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(text.top)
            },
            onState = { state ->
                isNoImageVisible = state.isErrorOrEmpty
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
                bottom.linkTo(text.top)
            },
            enter = fadeIn()
        ) {
            Text(
                text = stringResource(Res.string.no_image),
                style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.secondary)
            )
        }

        Text(
            text = movie.title,
            modifier = Modifier.constrainAs(text) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(image.bottom, 16.dp)
                end.linkTo(parent.end, 16.dp)
                bottom.linkTo(parent.bottom, 16.dp)
            },
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
private fun MovieColumnDesktopPreview(
    /*@PreviewParameter(MoviePreviewParameterProvider::class)*/ movie: MoviePojo
) {
    MoviesTheme {
        MovieColumnDesktop(
            movie = movie,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inversePrimary)
        )
    }
}