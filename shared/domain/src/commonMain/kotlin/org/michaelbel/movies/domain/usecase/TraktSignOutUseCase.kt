package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.CancellationException
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.network.TraktNetworkService
import org.michaelbel.movies.network.config.traktClientId
import org.michaelbel.movies.network.model.TraktRevokeRequest
import org.michaelbel.movies.persistence.datastore.TraktPreferences

class TraktSignOutUseCase(
    private val traktNetworkService: TraktNetworkService,
    private val traktPreferences: TraktPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        val accessToken = traktPreferences.getValue(TraktPreferences.PreferenceKey.PreferenceAccessTokenKey)

        if (!accessToken.isNullOrEmpty()) {
            try {
                val request = TraktRevokeRequest(
                    token = accessToken,
                    clientId = traktClientId
                )
                traktNetworkService.revokeToken(request)
            } catch (exception: CancellationException) {
                throw exception
            } catch (_: Exception) {
                // Revoke is best effort: local session is cleared regardless.
            }
        }

        traktPreferences.removeValues(
            TraktPreferences.PreferenceKey.PreferenceAccessTokenKey,
            TraktPreferences.PreferenceKey.PreferenceRefreshTokenKey,
            TraktPreferences.PreferenceKey.PreferenceTokenExpireTimeKey,
            TraktPreferences.PreferenceKey.PreferenceUserIdKey,
            TraktPreferences.PreferenceKey.PreferenceUsernameKey,
            TraktPreferences.PreferenceKey.PreferenceNameKey,
            TraktPreferences.PreferenceKey.PreferenceAvatarKey
        )
    }
}
