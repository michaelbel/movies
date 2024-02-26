package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.AccountDb

interface AccountRepository {

    val account: Flow<AccountDb?>

    suspend fun accountId(): Int

    suspend fun accountExpireTime(): Long?

    suspend fun accountDetails()
}