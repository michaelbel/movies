@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.persistence.datastore.TraktPreferences

class AccountPojoFlowUseCase(
    private val accountDao: AccountDao,
    private val preferences: MoviesPreferences,
    private val traktPreferences: TraktPreferences,
    dispatchers: SharedDispatchers
): FlowUseCase<Unit, AccountPojo>(dispatchers.io) {

    override fun execute(params: Unit): Flow<AccountPojo> {
        return preferences.getValueFlow(MoviesPreferences.PreferenceKey.PreferenceProviderKey).flatMapLatest { providerKey ->
            when (providerKey) {
                TRAKT_PROVIDER_KEY -> {
                    combine(
                        traktPreferences.getValueFlow(TraktPreferences.PreferenceKey.PreferenceAccessTokenKey),
                        traktPreferences.getValueFlow(TraktPreferences.PreferenceKey.PreferenceUserIdKey),
                        traktPreferences.getValueFlow(TraktPreferences.PreferenceKey.PreferenceUsernameKey),
                        traktPreferences.getValueFlow(TraktPreferences.PreferenceKey.PreferenceNameKey),
                        traktPreferences.getValueFlow(TraktPreferences.PreferenceKey.PreferenceAvatarKey)
                    ) { accessToken, userId, username, name, avatar ->
                        when {
                            accessToken.isNullOrEmpty() || username.isNullOrEmpty() -> {
                                AccountPojo.Empty
                            }
                            else -> {
                                AccountPojo(
                                    accountId = userId.orEmpty(),
                                    avatarUrl = avatar.orEmpty(),
                                    language = "",
                                    country = "",
                                    name = name.orEmpty().ifEmpty { username },
                                    adult = false,
                                    username = username
                                )
                            }
                        }
                    }
                }
                else -> {
                    val preferenceAccountKeyFlow = preferences.getValueFlow(MoviesPreferences.PreferenceKey.PreferenceAccountKey)
                    preferenceAccountKeyFlow
                        .map { accountId -> accountId.orEmpty() }
                        .flatMapLatest { accountId ->
                            accountDao.selectFlow(accountId).map { it ?: AccountPojo.Empty }
                        }
                }
            }
        }
    }

    private companion object {
        private const val TRAKT_PROVIDER_KEY = "trakt"
    }
}
