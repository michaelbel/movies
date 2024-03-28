package org.michaelbel.movies.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.AccountPojo

/**
 * The Data Access Object for the [AccountDb] class.
 */
@Dao
internal interface AccountDao {

    @Query("SELECT * FROM accounts WHERE accountId = :accountId")
    fun accountById(accountId: Int): Flow<AccountPojo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: AccountDb)

    @Query("DELETE FROM accounts WHERE accountId = :accountId")
    suspend fun removeById(accountId: Int)
}