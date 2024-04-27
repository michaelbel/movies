@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.accountDb
import org.michaelbel.movies.persistence.database.typealiases.AccountId

actual class AccountPersistence internal constructor(
    private val accountDao: AccountDao
) {

    actual fun accountById(accountId: AccountId): Flow<AccountPojo?> {
        return accountDao.accountById(accountId)
    }

    actual suspend fun insert(account: AccountPojo) {
        accountDao.insert(account.accountDb)
    }

    actual suspend fun removeById(accountId: AccountId) {
        accountDao.removeById(accountId)
    }
}