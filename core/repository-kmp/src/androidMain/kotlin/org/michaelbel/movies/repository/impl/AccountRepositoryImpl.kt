@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.network.ktor.KtorAccountService
import org.michaelbel.movies.network.retrofit.RetrofitAccountService
import org.michaelbel.movies.persistence.database.AccountPersistence
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.AccountRepository
import org.michaelbel.movies.repository.ktx.mapToAccountDb

/**
 * You can replace [ktorAccountService] with [retrofitAccountService] to use it.
 */
@Singleton
internal class AccountRepositoryImpl @Inject constructor(
    private val retrofitAccountService: RetrofitAccountService,
    private val ktorAccountService: KtorAccountService,
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
            val account = ktorAccountService.accountDetails(sessionId)
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