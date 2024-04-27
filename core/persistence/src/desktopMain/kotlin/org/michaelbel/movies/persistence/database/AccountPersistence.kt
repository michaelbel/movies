@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.AccountPojo
import org.michaelbel.movies.persistence.database.typealiases.AccountId

actual class AccountPersistence internal constructor() {

    actual fun accountById(
        accountId: AccountId
    ): Flow<AccountPojo?> {
        return emptyFlow()
    }

    actual suspend fun insert(
        account: AccountPojo
    ) {}

    actual suspend fun removeById(
        accountId: AccountId
    ) {}
}