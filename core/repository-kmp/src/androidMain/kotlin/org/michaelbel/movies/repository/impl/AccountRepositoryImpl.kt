@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.network.AccountNetworkService
import org.michaelbel.movies.persistence.database.AccountPersistence
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.AccountRepository
import org.michaelbel.movies.repository.ktx.mapToAccountDb

internal class AccountRepositoryImpl(
    private val accountNetworkService: AccountNetworkService,
    private val accountPersistence: AccountPersistence,
    private val preferences: MoviesPreferences
): AccountRepository {

    override val account: Flow<AccountDb?> = preferences.accountIdFlow
        .map { accountId -> accountId ?: 0 }
        .flatMapLatest(accountPersistence::accountById)

    override suspend fun accountId(): Int {
        return preferences.accountId()
    }

    override suspend fun accountExpireTime(): Long? {
        return preferences.accountExpireTime()
    }

    override suspend fun accountDetails() {
        runCatching {
            val sessionId = preferences.sessionId().orEmpty()
            val account = accountNetworkService.accountDetails(sessionId)
            preferences.run {
                setAccountId(account.id)
                setAccountExpireTime(System.currentTimeMillis())
            }
            accountPersistence.insert(account.mapToAccountDb)
        }.onFailure {
            throw AccountDetailsException
        }
    }
}