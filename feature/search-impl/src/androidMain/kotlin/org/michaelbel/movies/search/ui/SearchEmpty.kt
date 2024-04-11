package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.search_impl.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SearchEmpty(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = MoviesIcons.ManageSearch,
            contentDescription = MoviesContentDescription.None,
            modifier = Modifier.size(36.dp),
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = stringResource(R.string.search_results_empty),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Composable
@DevicePreviews
private fun SearchEmptyPreview() {
    MoviesTheme {
        SearchEmpty(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SearchEmptyAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SearchEmpty(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}