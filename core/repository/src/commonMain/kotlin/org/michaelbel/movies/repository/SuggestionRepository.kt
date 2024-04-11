package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.SuggestionPojo

interface SuggestionRepository {

    fun suggestions(): Flow<List<SuggestionPojo>>

    suspend fun updateSuggestions(
        language: String
    )
}