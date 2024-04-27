package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.AccountPojo
import org.michaelbel.movies.persistence.database.typealiases.AccountId

interface AccountRepository {

    val account: Flow<AccountPojo?>

    suspend fun accountId(): AccountId

    suspend fun accountExpireTime(): Long?

    suspend fun accountDetails()
}