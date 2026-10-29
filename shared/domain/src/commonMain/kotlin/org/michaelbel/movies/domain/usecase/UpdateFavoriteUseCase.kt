package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.network.AccountNetworkService
import org.michaelbel.movies.network.model.Fave
import org.michaelbel.movies.network.model.Mark
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import kotlin.time.Clock
import org.michaelbel.movies.domain.usecase.UpdateFavoriteUseCase.Params

class UpdateFavoriteUseCase(
    private val accountNetworkService: AccountNetworkService,
    private val moviePersistence: MoviePersistence,
    private val preferences: MoviesPreferences,
    dispatchers: SharedDispatchers
): UseCase<Params, Unit>(dispatchers.io) {

    override suspend fun execute(params: Params) {
        val sessionId = preferences.getValue(MoviesPreferences.PreferenceKey.PreferenceSessionIdKey).orEmpty()
        val accountId = preferences.getValue(MoviesPreferences.PreferenceKey.PreferenceAccountKey).orEmpty()
        if (sessionId.isEmpty() || accountId == 0) return
        val movie = moviePersistence.movieById(params.movieId) ?: return

        val mark = accountNetworkService.markAsFavorite(
            accountId = accountId,
            sessionId = sessionId,
            fave = Fave(
                mediaType = Movie.MOVIE,
                mediaId = params.movieId.toLong(),
                favorite = params.favorite
            )
        )

        if (mark.statusCode in setOf(Mark.ADDED, Mark.UPDATED, Mark.DELETED)) {
            if (params.favorite) {
                val maxPosition = moviePersistence.maxPosition(Movie.FAVORITE)
                moviePersistence.upsert(
                    movie.copy(
                        movieList = Movie.FAVORITE,
                        dateAdded = Clock.System.now().toEpochMilliseconds(),
                        position = maxPosition.plus(1)
                    )
                )
            } else {
                moviePersistence.removeMovie(Movie.FAVORITE, params.movieId)
            }
        }
    }

    data class Params(
        val movieId: MovieId,
        val favorite: Boolean
    )
}
