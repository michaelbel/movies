package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.repository.SuggestionRepository

class SuggestionRepositoryImpl(
    private val movieNetworkService: MovieNetworkService,
    private val moviePersistence: MoviePersistence,
    private val repositoryWebStore: RepositoryWebStore
): SuggestionRepository {

    override fun suggestions(): Flow<List<SuggestionPojo>> {
        return repositoryWebStore.suggestionsFlow()
    }

    override suspend fun updateSuggestions(language: String) {
        repositoryWebStore.clearSuggestions()

        val nowPlayingMovies = moviePersistence.movies(Movie.NOW_PLAYING, 5)
        when {
            nowPlayingMovies.isNotEmpty() -> repositoryWebStore.updateSuggestions(
                nowPlayingMovies.map { movie -> SuggestionPojo(movie.title) }
            )
            else -> {
                val suggestions = movieNetworkService.movies(
                    list = Movie.NOW_PLAYING,
                    language = language,
                    page = 1
                ).results.take(5)
                repositoryWebStore.updateSuggestions(
                    suggestions.map { movie -> SuggestionPojo(movie.title) }
                )
            }
        }
    }
}
