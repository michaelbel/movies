@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.SuggestionDao
import org.michaelbel.movies.persistence.database.entity.SuggestionPojo
import org.michaelbel.movies.persistence.database.ktx.suggestionDb

actual class SuggestionPersistence internal constructor(
    private val suggestionDao: SuggestionDao
) {

    actual fun suggestionsFlow(): Flow<List<SuggestionPojo>> {
        return suggestionDao.suggestionsFlow()
    }

    actual suspend fun insert(suggestions: List<SuggestionPojo>) {
        suggestionDao.insert(suggestions.map(SuggestionPojo::suggestionDb))
    }

    actual suspend fun removeAll() {
        suggestionDao.removeAll()
    }
}