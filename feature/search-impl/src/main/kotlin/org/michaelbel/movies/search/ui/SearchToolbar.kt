package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.search.ui.common.SwipeToDismiss
import org.michaelbel.movies.search_impl.R
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.CloseIcon
import org.michaelbel.movies.ui.compose.iconbutton.VoiceIcon
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.SuggestionDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SearchToolbar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onInputText: (String) -> Unit,
    suggestions: List<SuggestionDb>,
    searchHistoryMovies: List<MovieDb>,
    onHistoryMovieRemoveClick: (Int) -> Unit,
    onClearHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = stringResource(R.string.search_title)
            )
        },
        leadingIcon = {
            BackIcon(
                onClick = onBackClick
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                CloseIcon(
                    onClick = onCloseClick
                )
            } else {
                VoiceIcon(
                    onInputText = onInputText
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.inversePrimary,
            dividerColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        when {
            searchHistoryMovies.isNotEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                ) {
                    SearchHistoryHeader(
                        onClearButtonClick = onClearHistoryClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )

                    val movies by remember { mutableStateOf(searchHistoryMovies) }

                    LazyColumn {
                        items(movies) { movieDb ->
                            SwipeToDismiss(
                                item = movieDb,
                                onDelete = { deletedMovieDb ->
                                    onHistoryMovieRemoveClick(deletedMovieDb.movieId)
                                }
                            ) { swipedMovieDb ->
                                SearchRecentResult(
                                    text = swipedMovieDb.title,
                                    onRemoveClick = { onHistoryMovieRemoveClick(swipedMovieDb.movieId) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp)
                                        .background(MaterialTheme.colorScheme.inversePrimary)
                                        .clickable { onInputText(swipedMovieDb.title) }
                                )
                            }
                        }
                    }
                }
            }
            suggestions.isNotEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn {
                        items(suggestions) { suggestion ->
                            SearchSuggestion(
                                text = suggestion.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .clickable { onInputText(suggestion.title) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SearchToolbarPreview(
    @PreviewParameter(SuggestionDbPreviewParameterProvider::class) suggestions: List<SuggestionDb>
) {
    MoviesTheme {
        SearchToolbar(
            query = "Napoleon",
            onQueryChange = {},
            onSearch = {},
            active = true,
            onActiveChange = {},
            onBackClick = {},
            onCloseClick = {},
            onInputText = {},
            suggestions = suggestions,
            searchHistoryMovies = emptyList(),
            onHistoryMovieRemoveClick = {},
            onClearHistoryClick = {}
        )
    }
}

@Composable
@Preview
private fun SearchToolbarAmoledPreview(
    @PreviewParameter(SuggestionDbPreviewParameterProvider::class) suggestions: List<SuggestionDb>
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SearchToolbar(
            query = "Napoleon",
            onQueryChange = {},
            onSearch = {},
            active = true,
            onActiveChange = {},
            onBackClick = {},
            onCloseClick = {},
            onInputText = {},
            suggestions = suggestions,
            searchHistoryMovies = emptyList(),
            onHistoryMovieRemoveClick = {},
            onClearHistoryClick = {}
        )
    }
}