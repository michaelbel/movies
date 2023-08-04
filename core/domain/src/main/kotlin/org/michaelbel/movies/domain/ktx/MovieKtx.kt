package org.michaelbel.movies.domain.ktx

import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.entities.image.formatBackdropImage
import org.michaelbel.movies.network.model.Movie

internal val Movie.mapToMovieDb: MovieDb
    get() = MovieDb(
        movieList = "",
        dateAdded = 0L,
        position = 0,
        movieId = id,
        overview = overview.orEmpty(),
        posterPath = posterPath.orEmpty(),
        backdropPath = backdropPath.orEmpty().formatBackdropImage,
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        voteAverage = voteAverage
    )