@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.SuggestionPojo

actual class SuggestionPersistence internal constructor() {

    fun suggestionsFlow(): Flow<List<SuggestionPojo>> {
        return emptyFlow()
    }

    suspend fun insert(suggestions: List<SuggestionPojo>) {}

    suspend fun removeAll() {}
}