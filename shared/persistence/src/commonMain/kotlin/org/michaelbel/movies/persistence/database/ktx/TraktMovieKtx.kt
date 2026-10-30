package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.model.TraktMovieResponse
import org.michaelbel.movies.persistence.database.entity.TraktMovieDb
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Position
import kotlin.time.Clock

fun TraktMovieResponse.traktMovieDb(
    traktList: PagingKey,
    page: Page,
    position: Position
): TraktMovieDb {
    return TraktMovieDb(
        traktList = traktList,
        dateAdded = Clock.System.now().toEpochMilliseconds(),
        page = page,
        position = position,
        traktId = ids.trakt,
        tmdbId = ids.tmdb,
        overview = overview.orEmpty(),
        posterPath = when (val poster = images?.poster?.firstOrNull()) {
            null -> ""
            else -> when {
                poster.startsWith("http") -> poster
                else -> "https://$poster"
            }
        },
        backdropPath = when (val fanart = images?.fanart?.firstOrNull()) {
            null -> ""
            else -> when {
                fanart.startsWith("http") -> fanart
                else -> "https://$fanart"
            }
        },
        releaseDate = released.orEmpty(),
        title = title.orEmpty(),
        voteAverage = rating.orEmpty()
    )
}

val TraktMovieDb.moviePojo: MoviePojo
    get() = MoviePojo(
        movieList = traktList,
        dateAdded = dateAdded,
        page = page,
        position = position,
        movieId = traktId,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
