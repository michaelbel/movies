package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.service.movie.MovieService
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.repository.SuggestionRepository

@Singleton
internal class SuggestionRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val suggestionDao: SuggestionDao,
    private val localeController: LocaleController
): SuggestionRepository {

    override fun suggestions(): Flow<List<SuggestionDb>> {
        return suggestionDao.suggestionsFlow()
    }

    override suspend fun updateSuggestions() {
        suggestionDao.removeAll()

        val nowPlayingMovies = movieDao.movies(Movie.NOW_PLAYING, 5)
        if (nowPlayingMovies.isNotEmpty()) {
            suggestionDao.insert(nowPlayingMovies.map { movieDb -> SuggestionDb(movieDb.title) })
        } else {
            val movieResponse = movieService.movies(
                list = Movie.NOW_PLAYING,
                language = localeController.language,
                page = 1
            ).results.take(5)
            suggestionDao.insert(movieResponse.map { movie -> SuggestionDb(movie.title) })
        }
    }
}