package org.michaelbel.movies.domain.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.domain.data.entity.AccountDb

/**
 * The Data Access Object for the [AccountDb] class.
 */
@Dao
internal interface AccountDao {

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun accountById(accountId: Int): Flow<AccountDb?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountDb)

    @Query("DELETE FROM accounts WHERE id = :accountId")
    suspend fun removeById(accountId: Int)
}