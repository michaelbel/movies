package org.michaelbel.movies.persistence.database.ktx

import java.util.Locale
import org.michaelbel.movies.network.config.TMDB_MOVIE_URL
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo

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

val MoviePojo.isNotEmpty: Boolean
    get() = this != MoviePojo.Empty

val MoviePojo.url: String
    get() = String.format(Locale.US, TMDB_MOVIE_URL, movieId)

val MoviePojo?.orEmpty: MoviePojo
    get() = this ?: MoviePojo.Empty