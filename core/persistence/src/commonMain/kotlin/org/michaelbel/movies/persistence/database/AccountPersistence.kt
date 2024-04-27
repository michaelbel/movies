@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.AccountPojo
import org.michaelbel.movies.persistence.database.typealiases.AccountId

expect class AccountPersistence {

    fun accountById(
        accountId: AccountId
    ): Flow<AccountPojo?>

    suspend fun insert(
        account: AccountPojo
    )

    suspend fun removeById(
        accountId: AccountId
    )
}