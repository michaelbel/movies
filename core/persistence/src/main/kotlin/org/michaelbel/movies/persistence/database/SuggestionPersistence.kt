package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import javax.inject.Inject

class SuggestionPersistence @Inject internal constructor(
    private val suggestionDao: SuggestionDao
) {

    fun suggestionsFlow(): Flow<List<SuggestionDb>> {
        return suggestionDao.suggestionsFlow()
    }

    suspend fun insert(suggestions: List<SuggestionDb>) {
        suggestionDao.insert(suggestions)
    }

    suspend fun removeAll() {
        suggestionDao.removeAll()
    }
}