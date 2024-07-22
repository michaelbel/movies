package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.accountDb
import org.michaelbel.movies.persistence.database.typealiases.AccountId

class AccountPersistence internal constructor(
    private val moviesDatabase: MoviesDatabase
) {

    fun accountById(accountId: AccountId): Flow<AccountPojo?> {
        return moviesDatabase.accountDao.accountById(accountId)
    }

    suspend fun insert(account: AccountPojo) {
        moviesDatabase.accountDao.insert(account.accountDb)
    }

    suspend fun removeById(accountId: AccountId) {
        moviesDatabase.accountDao.removeById(accountId)
    }
}