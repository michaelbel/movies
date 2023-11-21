package org.michaelbel.movies.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.network.service.account.AccountService
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.ktx.mapToAccountDb

@Singleton
internal class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService,
    private val accountDao: AccountDao,
    private val preferences: MoviesPreferences
): AccountRepository {

    override val account: Flow<AccountDb?> = preferences.accountIdFlow
        .map { accountId -> accountId ?: 0 }
        .flatMapLatest(accountDao::accountById)

    override suspend fun accountId(): Int? {
        return preferences.accountId()
    }

    override suspend fun accountExpireTime(): Long? {
        return preferences.accountExpireTime()
    }

    override suspend fun accountDetails() {
        try {
            val sessionId: String = preferences.sessionId().orEmpty()
            val account: Account = accountService.accountDetails(sessionId)
            preferences.run {
                setAccountId(account.id)
                setAccountExpireTime(System.currentTimeMillis())
            }
            accountDao.insert(account.mapToAccountDb)
        } catch (ignored: Exception) {
            throw AccountDetailsException
        }
    }
}