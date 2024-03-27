package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.MoviePojo

internal val MoviePojo.movieDb: MovieDb
    get() = MovieDb(
        movieList = movieList,
        dateAdded = dateAdded,
        page = page,
        position = position,
        movieId = movieId,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        containerColor = containerColor,
        onContainerColor = onContainerColor
    )