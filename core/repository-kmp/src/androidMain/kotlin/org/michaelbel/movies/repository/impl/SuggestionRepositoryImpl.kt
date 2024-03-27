package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.SuggestionPersistence
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.repository.SuggestionRepository

internal class SuggestionRepositoryImpl(
    private val movieNetworkService: MovieNetworkService,
    private val moviePersistence: MoviePersistence,
    private val suggestionPersistence: SuggestionPersistence,
    private val localeController: LocaleController
): SuggestionRepository {

    override fun suggestions(): Flow<List<SuggestionDb>> {
        return suggestionPersistence.suggestionsFlow()
    }

    override suspend fun updateSuggestions() {
        suggestionPersistence.removeAll()

        val nowPlayingMovies = moviePersistence.movies(Movie.NOW_PLAYING, 5)
        if (nowPlayingMovies.isNotEmpty()) {
            suggestionPersistence.insert(nowPlayingMovies.map { movieDb -> SuggestionDb(movieDb.title) })
        } else {
            val movieResponse = movieNetworkService.movies(
                list = Movie.NOW_PLAYING,
                language = localeController.language,
                page = 1
            ).results.take(5)
            suggestionPersistence.insert(movieResponse.map { movie -> SuggestionDb(movie.title) })
        }
    }
}