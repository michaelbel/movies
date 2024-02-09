package org.michaelbel.movies.repository.ktx

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MovieDb

internal fun MovieResponse.mapToMovieDb(movieList: String, page: Int, position: Int): MovieDb {
    return MovieDb(
        movieList = movieList,
        dateAdded = System.currentTimeMillis(),
        page = page,
        position = position,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        containerColor = null,
        onContainerColor = null
    )
}