@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.compose.movie

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.persistence.database.entity.MovieDb

@Composable
expect fun MovieRow(
    movie: MovieDb,
    modifier: Modifier = Modifier,
    maxLines: Int = 10
)