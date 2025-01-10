package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo

val Movie.moviePojo: MoviePojo
    get() = MoviePojo(
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