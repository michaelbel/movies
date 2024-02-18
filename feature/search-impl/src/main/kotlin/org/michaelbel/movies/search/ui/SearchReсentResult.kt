package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.compose.iconbutton.CloseIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MoviePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SearchRecentResult(
    text: String,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (historyIcon, recentResultText, clearButton) = createRefs()

        Icon(
            imageVector = MoviesIcons.History,
            contentDescription = stringResource(MoviesContentDescription.HistoryIcon),
            modifier = Modifier.constrainAs(historyIcon) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Text(
            text = text,
            modifier = Modifier.constrainAs(recentResultText) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                start.linkTo(historyIcon.end, 8.dp)
                top.linkTo(parent.top)
                end.linkTo(clearButton.start, 8.dp)
                bottom.linkTo(parent.bottom)
            },
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        CloseIcon(
            onClick = onRemoveClick,
            modifier = Modifier.constrainAs(clearButton) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
@DevicePreviews
private fun SearchRecentResultPreview(
    @PreviewParameter(MoviePreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme {
        SearchRecentResult(
            text = movie.title,
            onRemoveClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SearchRecentResultAmoledPreview(
    @PreviewParameter(MoviePreviewParameterProvider::class) movie: MovieDb
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SearchRecentResult(
            text = movie.title,
            onRemoveClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}