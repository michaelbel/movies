package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.persistence.database.ktx.suggestionDb

class SuggestionPersistence internal constructor(
    private val moviesDatabase: MoviesDatabase
) {

    fun suggestionsFlow(): Flow<List<SuggestionPojo>> {
        return moviesDatabase.suggestionDao.suggestionsFlow()
    }

    suspend fun insert(suggestions: List<SuggestionPojo>) {
        moviesDatabase.suggestionDao.insert(suggestions.map(SuggestionPojo::suggestionDb))
    }

    suspend fun removeAll() {
        moviesDatabase.suggestionDao.removeAll()
    }
}