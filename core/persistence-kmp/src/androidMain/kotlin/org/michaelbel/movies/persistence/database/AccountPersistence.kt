@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.accountDb

actual class AccountPersistence internal constructor(
    private val accountDao: AccountDao
) {

    fun accountById(accountId: Int): Flow<AccountPojo?> {
        return accountDao.accountById(accountId)
    }

    suspend fun insert(account: AccountPojo) {
        accountDao.insert(account.accountDb)
    }

    suspend fun removeById(accountId: Int) {
        accountDao.removeById(accountId)
    }
}