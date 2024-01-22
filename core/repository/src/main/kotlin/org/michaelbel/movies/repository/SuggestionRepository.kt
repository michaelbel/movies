package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

interface SuggestionRepository {

    fun suggestions(): Flow<List<SuggestionDb>>

    suspend fun updateSuggestions()
}