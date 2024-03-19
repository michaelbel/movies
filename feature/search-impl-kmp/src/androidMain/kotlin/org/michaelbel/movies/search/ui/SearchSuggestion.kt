package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.SuggestionDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SearchSuggestion(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
@DevicePreviews
private fun SearchSuggestionPreview(
    @PreviewParameter(SuggestionDbPreviewParameterProvider::class) suggestions: List<SuggestionDb>
) {
    MoviesTheme {
        SearchSuggestion(
            text = suggestions.first().title,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun SearchSuggestionAmoledPreview(
    @PreviewParameter(SuggestionDbPreviewParameterProvider::class) suggestions: List<SuggestionDb>
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SearchSuggestion(
            text = suggestions.first().title,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}