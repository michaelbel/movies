@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose.page

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.persistence.database.entity.MovieDb

@Composable
expect fun PageContent(
    feedView: FeedView,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: LazyPagingItems<MovieDb>,
    onMovieClick: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
)