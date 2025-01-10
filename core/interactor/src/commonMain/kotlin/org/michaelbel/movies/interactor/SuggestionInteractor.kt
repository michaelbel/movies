package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo

interface SuggestionInteractor {

    fun suggestions(): Flow<List<SuggestionPojo>>

    suspend fun updateSuggestions()
}