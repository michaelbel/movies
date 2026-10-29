package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.network.AccountNetworkService
import org.michaelbel.movies.persistence.database.AccountPersistence
import org.michaelbel.movies.persistence.database.ktx.accountPojo
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import kotlin.time.Clock

class AccountDetailsUseCase(
    private val accountNetworkService: AccountNetworkService,
    private val accountPersistence: AccountPersistence,
    private val preferences: MoviesPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        try {
            val sessionId = preferences.getValue(MoviesPreferences.PreferenceKey.PreferenceSessionIdKey).orEmpty()
            val account = accountNetworkService.accountDetails(sessionId)
            preferences.run {
                setValue(MoviesPreferences.PreferenceKey.PreferenceAccountKey, account.id)
                setValue(MoviesPreferences.PreferenceKey.PreferenceAccountExpireTimeKey, Clock.System.now().toEpochMilliseconds())
            }
            accountPersistence.upsert(account.accountPojo)
        } catch (exception: Exception) { throw AccountDetailsException(exception.message.orEmpty()) }
    }

    data class AccountDetailsException(
        override val message: String
    ): Exception(message)
}
