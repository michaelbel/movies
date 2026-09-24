package org.michaelbel.movies.persistence.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.typealiases.AccountId

/**
 * The Data Access Object for the [AccountDb] class.
 */
@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE accountId = :accountId")
    fun selectFlow(accountId: AccountId): Flow<AccountPojo?>

    @Upsert
    suspend fun upsert(account: AccountDb)

    @Query("DELETE FROM accounts WHERE accountId = :accountId")
    suspend fun removeById(accountId: AccountId)
}
