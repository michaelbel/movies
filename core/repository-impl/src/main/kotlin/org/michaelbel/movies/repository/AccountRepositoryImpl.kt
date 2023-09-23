package org.michaelbel.movies.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.entities.tmdbApiKey
import org.michaelbel.movies.network.model.Account
import org.michaelbel.movies.network.service.account.AccountService
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.ktx.mapToAccountDb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService,
    private val accountDao: AccountDao,
    private val preferences: MoviesPreferences
): AccountRepository {

    override val account: Flow<AccountDb?> = preferences.accountId
        .map { accountId -> accountId ?: 0 }
        .flatMapLatest(accountDao::accountById)

    override suspend fun accountDetails() {
        try {
            val sessionId: String = preferences.getSessionId().orEmpty()
            val account: Account = accountService.accountDetails(
                apiKey = tmdbApiKey,
                sessionId = sessionId
            )
            preferences.run {
                setAccountId(account.id)
                setAccountExpireTime(System.currentTimeMillis())
            }
            accountDao.insert(account.mapToAccountDb)
        } catch (e: Exception) {
            throw AccountDetailsException
        }
    }
}