package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.SuggestionInteractor
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.repository.SuggestionRepository

internal class SuggestionInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val localeInteractor: LocaleInteractor,
    private val suggestionRepository: SuggestionRepository
): SuggestionInteractor {

    override fun suggestions(): Flow<List<SuggestionPojo>> {
        return suggestionRepository.suggestions()
    }

    override suspend fun updateSuggestions() {
        withContext(dispatchers.io) { suggestionRepository.updateSuggestions(localeInteractor.language) }
    }
}