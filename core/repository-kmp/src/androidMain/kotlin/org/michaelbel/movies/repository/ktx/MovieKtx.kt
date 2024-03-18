package org.michaelbel.movies.repository.ktx

import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.entity.MovieDb

internal val Movie.mapToMovieDb: MovieDb
    get() = MovieDb(
        movieList = "",
        dateAdded = 0L,
        page = null,
        position = 0,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        voteAverage = voteAverage,
        containerColor = null,
        onContainerColor = null
    )