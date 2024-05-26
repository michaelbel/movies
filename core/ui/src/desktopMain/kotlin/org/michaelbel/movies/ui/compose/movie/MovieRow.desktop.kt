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
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.network.config.formatBackdropImage
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun MovieRow(
    movie: MoviePojo,
    modifier: Modifier = Modifier,
    maxLines: Int = 10
) {
    var isNoImageVisible by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (image, noImageText, text) = createRefs()

        KamelImage(
            resource = asyncPainterResource(movie.backdropPath.formatBackdropImage),
            contentDescription = null,
            modifier = Modifier.constrainAs(image) {
                width = Dimension.fillToConstraints
                height = Dimension.value(220.dp)
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            onFailure = {
                isNoImageVisible = true
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
                text = "No image",
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
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
private fun MovieRowPreview(
    /*@PreviewParameter(MoviePreviewParameterProvider::class)*/ movie: MoviePojo
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

@Composable
private fun MovieRowAmoledPreview(
    /*@PreviewParameter(MoviePreviewParameterProvider::class)*/ movie: MoviePojo
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
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