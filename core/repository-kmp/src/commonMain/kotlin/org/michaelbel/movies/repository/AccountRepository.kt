package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.AccountPojo

interface AccountRepository {

    val account: Flow<AccountPojo?>

    suspend fun accountId(): Int

    suspend fun accountExpireTime(): Long?

    suspend fun accountDetails()
}