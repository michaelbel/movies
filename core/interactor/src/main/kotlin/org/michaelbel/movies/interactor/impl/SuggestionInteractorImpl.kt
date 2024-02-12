package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.SuggestionInteractor
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.repository.SuggestionRepository

@Singleton
internal class SuggestionInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val suggestionRepository: SuggestionRepository
): SuggestionInteractor {

    override fun suggestions(): Flow<List<SuggestionDb>> {
        return suggestionRepository.suggestions()
    }

    override suspend fun updateSuggestions() {
        withContext(dispatchers.io) { suggestionRepository.updateSuggestions() }
    }
}