@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.SuggestionPojo

expect class SuggestionPersistence {

    fun suggestionsFlow(): Flow<List<SuggestionPojo>>

    suspend fun insert(
        suggestions: List<SuggestionPojo>
    )

    suspend fun removeAll()
}