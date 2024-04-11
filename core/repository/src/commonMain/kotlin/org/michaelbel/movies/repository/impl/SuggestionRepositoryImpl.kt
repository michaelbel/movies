package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.SuggestionPersistence
import org.michaelbel.movies.persistence.database.entity.SuggestionPojo
import org.michaelbel.movies.repository.SuggestionRepository

internal class SuggestionRepositoryImpl(
    private val movieNetworkService: MovieNetworkService,
    private val moviePersistence: MoviePersistence,
    private val suggestionPersistence: SuggestionPersistence
): SuggestionRepository {

    override fun suggestions(): Flow<List<SuggestionPojo>> {
        return suggestionPersistence.suggestionsFlow()
    }

    override suspend fun updateSuggestions(
        language: String
    ) {
        suggestionPersistence.removeAll()

        val nowPlayingMovies = moviePersistence.movies(Movie.NOW_PLAYING, 5)
        if (nowPlayingMovies.isNotEmpty()) {
            suggestionPersistence.insert(nowPlayingMovies.map { movie -> SuggestionPojo(movie.title) })
        } else {
            val movieResponse = movieNetworkService.movies(
                list = Movie.NOW_PLAYING,
                language = language,
                page = 1
            ).results.take(5)
            suggestionPersistence.insert(movieResponse.map { movie -> SuggestionPojo(movie.title) })
        }
    }
}