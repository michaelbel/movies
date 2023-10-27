package org.michaelbel.movies.repository.ktx

import org.michaelbel.movies.entities.image.formatBackdropImage
import org.michaelbel.movies.entities.image.formatPosterImage
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MovieDb

internal fun MovieResponse.mapToMovieDb(movieList: String, position: Int): MovieDb {
    return MovieDb(
        movieList = movieList,
        dateAdded = System.currentTimeMillis(),
        position = position,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty().formatPosterImage,
        backdropPath = backdropPath.orEmpty().formatBackdropImage,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
}