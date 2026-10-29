package org.michaelbel.movies.webui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.webui.MovieCard
import org.michaelbel.movies.ui.compose.plus

@Composable
fun PageLoading(
    feedView: FeedView,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    cardColor: Color = MaterialTheme.colorScheme.inversePrimary
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 280.dp),
        modifier = modifier,
        contentPadding = contentPadding + PaddingValues(8.dp),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(
            count = MovieResponse.DEFAULT_PAGE_SIZE.div(2)
        ) {
            MovieCard(
                imageUrl = "",
                title = "",
                feedView = feedView,
                placeholder = true,
                cardColor = cardColor
            )
        }
    }
}
