package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.Position

fun MovieResponse.moviePojo(
    movieList: String,
    position: Position,
    page: Page? = null
): MoviePojo {
    return MoviePojo(
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