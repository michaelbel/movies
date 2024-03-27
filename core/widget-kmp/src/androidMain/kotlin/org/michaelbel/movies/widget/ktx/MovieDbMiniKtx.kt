package org.michaelbel.movies.widget.ktx

import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.widget.entity.MovieData

internal val MovieDbMini.mapToMovieData: MovieData
    get() = MovieData(
        movieList = movieList,
        movieId = movieId,
        movieTitle = title
    )