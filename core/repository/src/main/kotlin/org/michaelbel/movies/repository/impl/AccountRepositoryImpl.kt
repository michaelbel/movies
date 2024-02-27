package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.network.ktor.KtorAccountService
import org.michaelbel.movies.network.retrofit.RetrofitAccountService
import org.michaelbel.movies.persistence.database.dao.AccountDao
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
    private val accountDao: AccountDao,
    private val preferences: MoviesPreferences
): AccountRepository {

    override val account: Flow<AccountDb?> = preferences.accountIdFlow
        .map { accountId -> accountId ?: 0 }
        .flatMapLatest(accountDao::accountById)

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
            accountDao.insert(account.mapToAccountDb)
        }.onFailure {
            throw AccountDetailsException
        }
    }
}