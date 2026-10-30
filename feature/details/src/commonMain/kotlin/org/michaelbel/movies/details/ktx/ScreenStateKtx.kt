package org.michaelbel.movies.details.ktx

import androidx.compose.runtime.Composable
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo

val ScreenState.Content<*>.movie: MoviePojo
    get() = data as MoviePojo

val ScreenState.toolbarTitle: String
    @Composable get() = when (this) {
        is ScreenState.Loading -> ""
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> ""
    }
