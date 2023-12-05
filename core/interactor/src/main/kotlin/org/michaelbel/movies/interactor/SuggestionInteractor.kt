package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

interface SuggestionInteractor {

    fun suggestions(): Flow<List<SuggestionDb>>

    suspend fun updateSuggestions()
}