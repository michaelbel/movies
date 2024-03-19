@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

@Composable
expect fun SearchToolbar(
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
)