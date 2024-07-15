package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.url
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun ShareIcon(
    url: String,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    IconButton(
        onClick = { onShareClick(url) },
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.Share,
            contentDescription = stringResource(MoviesContentDescriptionCommon.ShareIcon),
            colorFilter = ColorFilter.tint(onContainerColor)
        )
    }
}

@Composable
/*@DevicePreviews*/
private fun ShareIconPreview(
    /*@PreviewParameter(MovieDbPreviewParameterProvider::class)*/ movie: MoviePojo
) {
    MoviesTheme {
        ShareIcon(
            url = movie.url,
            onShareClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
/*@Preview*/
private fun ShareIconAmoledPreview(
    /*@PreviewParameter(MovieDbPreviewParameterProvider::class)*/ movie: MoviePojo
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        ShareIcon(
            url = movie.url,
            onShareClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}