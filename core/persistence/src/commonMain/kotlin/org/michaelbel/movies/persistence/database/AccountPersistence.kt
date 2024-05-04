package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.accountDb
import org.michaelbel.movies.persistence.database.typealiases.AccountId

class AccountPersistence internal constructor(
    private val accountDao: AccountDao
) {

    fun accountById(accountId: AccountId): Flow<AccountPojo?> {
        return accountDao.accountById(accountId)
    }

    suspend fun insert(account: AccountPojo) {
        accountDao.insert(account.accountDb)
    }

    suspend fun removeById(accountId: AccountId) {
        accountDao.removeById(accountId)
    }
}