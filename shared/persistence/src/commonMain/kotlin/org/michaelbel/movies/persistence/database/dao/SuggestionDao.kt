package org.michaelbel.movies.persistence.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo

/**
 * The Data Access Object for the [SuggestionDb] class.
 */
@Dao
interface SuggestionDao {

    @Query("SELECT * FROM suggestions")
    fun selectFlow(): Flow<List<SuggestionPojo>>

    @Upsert
    suspend fun upsert(suggestions: List<SuggestionDb>)

    @Query("DELETE FROM suggestions")
    suspend fun removeAll()
}
