package org.michaelbel.movies.ui.compose.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.compose.movie.MovieColumn
import org.michaelbel.movies.ui.compose.movie.MovieRow
import org.michaelbel.movies.ui.ktx.PageContentColumnModifier
import org.michaelbel.movies.ui.ktx.PageContentGridModifier
import org.michaelbel.movies.ui.ktx.PageContentStaggeredGridModifier
import org.michaelbel.movies.ui.ktx.gridColumnsCount
import org.michaelbel.movies.ui.ktx.isPagingFailure
import org.michaelbel.movies.ui.ktx.isPagingLoading
import org.michaelbel.movies.ui.ktx.isPortrait
import org.michaelbel.movies.ui.ktx.retry

@Composable
fun PageContent(
    feedView: FeedView,
    lazyListState: LazyListState,
    lazyGridState: LazyGridState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: List<MoviePojo>,
    onMovieClick: (String, Int) -> Unit,
    modifier: Modifier,
    contentPadding: PaddingValues
) {
    when (feedView) {
        is FeedView.FeedList -> {
            if (isPortrait) {
                PageContentColumn(
                    lazyListState = lazyListState,
                    pagingItems = pagingItems,
                    onMovieClick = onMovieClick,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            } else {
                PageContentGrid(
                    lazyGridState = lazyGridState,
                    pagingItems = pagingItems,
                    onMovieClick = onMovieClick,
                    contentPadding = contentPadding,
                    modifier = modifier
                )
            }
        }
        is FeedView.FeedGrid -> {
            PageContentStaggeredGrid(
                lazyStaggeredGridState = lazyStaggeredGridState,
                pagingItems = pagingItems,
                onMovieClick = onMovieClick,
                contentPadding = contentPadding,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun PageContentColumn(
    lazyListState: LazyListState,
    pagingItems: List<MoviePojo>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(top = 4.dp),
        state = lazyListState,
        contentPadding = contentPadding
    ) {
        items(
            pagingItems
        ) { movieDb ->
            MovieRow(
                movie = movieDb,
                modifier = PageContentColumnModifier.then(Modifier.clickable { onMovieClick(movieDb.movieList, movieDb.movieId) })
            )
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier.fillMaxWidth().height(80.dp)
                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(start = 8.dp, top = 4.dp, end = 8.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContentGrid(
    lazyGridState: LazyGridState,
    pagingItems: List<MoviePojo>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp),
        state = lazyGridState,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pagingItems) { movieDb ->
            MovieRow(
                movie = movieDb,
                maxLines = 1,
                modifier = PageContentGridModifier.then(Modifier.clickable { onMovieClick(movieDb.movieList, movieDb.movieId) })
            )
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier.fillMaxWidth().height(80.dp)

                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContentStaggeredGrid(
    lazyStaggeredGridState: LazyStaggeredGridState,
    pagingItems: List<MoviePojo>,
    onMovieClick: (String, Int) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(gridColumnsCount),
        modifier = modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
        state = lazyStaggeredGridState,
        contentPadding = contentPadding,
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            pagingItems
        ) { movieDb ->
            MovieColumn(
                movie = movieDb,
                modifier = PageContentStaggeredGridModifier.then(Modifier.clickable { onMovieClick(movieDb.movieList, movieDb.movieId) })
            )
        }
        pagingItems.apply {
            when {
                isPagingLoading -> {
                    item {
                        PagingLoadingBox(
                            modifier = Modifier.fillMaxWidth().height(80.dp)

                        )
                    }
                }
                isPagingFailure -> {
                    item {
                        PagingFailureBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable { retry() }
                        )
                    }
                }
            }
        }
    }
}