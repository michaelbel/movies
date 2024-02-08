package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.search_impl.R
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SearchHistoryHeader(
    onClearButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (recentSearchesText, clearButton) = createRefs()

        Text(
            text = stringResource(R.string.search_recent),
            modifier = Modifier.constrainAs(recentSearchesText) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        TextButton(
            onClick = onClearButtonClick,
            modifier = Modifier.constrainAs(clearButton) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Text(
                text = stringResource(R.string.search_clear)
            )
        }
    }
}

@Composable
@DevicePreviews
private fun SearchHistoryHeaderPreview() {
    MoviesTheme {
        SearchHistoryHeader(
            onClearButtonClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SearchHistoryHeaderAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SearchHistoryHeader(
            onClearButtonClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}