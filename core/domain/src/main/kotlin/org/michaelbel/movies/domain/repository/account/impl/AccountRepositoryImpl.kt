package org.michaelbel.movies.domain.repository.account.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.domain.data.dao.AccountDao
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.exceptions.AccountDetailsException
import org.michaelbel.movies.domain.ktx.mapToAccountDb
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_ACCOUNT_ID_KEY
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_SESSION_ID_KEY
import org.michaelbel.movies.domain.repository.account.AccountRepository
import org.michaelbel.movies.domain.service.account.AccountService
import org.michaelbel.movies.entities.tmdbApiKey
import org.michaelbel.movies.network.model.Account
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService,
    private val accountDao: AccountDao,
    private val dataStore: DataStore<Preferences>
): AccountRepository {

    override val account: Flow<AccountDb?> = dataStore.data
        .map { preferences -> preferences[PREFERENCE_ACCOUNT_ID_KEY] ?: 0 }
        .flatMapLatest(accountDao::accountById)

    override suspend fun accountDetails() {
        try {
            val sessionId: String = dataStore.data.first()[PREFERENCE_SESSION_ID_KEY].orEmpty()
            val account: Account = accountService.accountDetails(
                apiKey = tmdbApiKey,
                sessionId = sessionId
            )
            dataStore.edit { preferences ->
                preferences[PREFERENCE_ACCOUNT_ID_KEY] = account.id
            }
            accountDao.insert(account.mapToAccountDb)
        } catch (e: Exception) {
            throw AccountDetailsException
        }
    }
}