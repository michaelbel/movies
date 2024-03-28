package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MoviePojo

fun MovieResponse.moviePojo(
    movieList: String,
    position: Int,
    page: Int? = null
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