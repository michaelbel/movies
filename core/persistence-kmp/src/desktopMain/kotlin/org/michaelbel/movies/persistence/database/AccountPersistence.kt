@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.AccountPojo

actual class AccountPersistence internal constructor() {

    fun accountById(accountId: Int): Flow<AccountPojo?> {
        return emptyFlow()
    }

    suspend fun insert(account: AccountPojo) {}

    suspend fun removeById(accountId: Int) {}
}