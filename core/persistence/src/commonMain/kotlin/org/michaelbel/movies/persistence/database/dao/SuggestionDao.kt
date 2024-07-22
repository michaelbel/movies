package org.michaelbel.movies.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo

/**
 * The Data Access Object for the [SuggestionDb] class.
 */
@Dao
interface SuggestionDao {

    @Query("SELECT * FROM suggestions")
    fun suggestionsFlow(): Flow<List<SuggestionPojo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(suggestions: List<SuggestionDb>)

    @Query("DELETE FROM suggestions")
    suspend fun removeAll()
}