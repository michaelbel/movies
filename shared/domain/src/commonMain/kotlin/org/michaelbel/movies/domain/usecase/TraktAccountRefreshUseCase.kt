package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.CancellationException
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.domain.usecase.TraktAccountRefreshUseCase.TraktAccountRefreshException
import org.michaelbel.movies.network.TraktNetworkService
import org.michaelbel.movies.network.config.TRAKT_REDIRECT_URI
import org.michaelbel.movies.network.config.traktClientId
import org.michaelbel.movies.network.model.TraktRefreshTokenRequest
import org.michaelbel.movies.persistence.datastore.TraktPreferences
import kotlin.time.Clock

class TraktAccountRefreshUseCase(
    private val traktNetworkService: TraktNetworkService,
    private val traktPreferences: TraktPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        try {
            var accessToken = traktPreferences.getValue(TraktPreferences.PreferenceKey.PreferenceAccessTokenKey)
            if (accessToken.isNullOrEmpty()) {
                return
            }

            val expireTime = traktPreferences.getValue(TraktPreferences.PreferenceKey.PreferenceTokenExpireTimeKey) ?: 0L
            if (expireTime - Clock.System.now().epochSeconds < REFRESH_THRESHOLD_SECONDS) {
                val refreshToken = traktPreferences.getValue(TraktPreferences.PreferenceKey.PreferenceRefreshTokenKey).orEmpty()
                val request = TraktRefreshTokenRequest(
                    refreshToken = refreshToken,
                    clientId = traktClientId,
                    redirectUri = TRAKT_REDIRECT_URI,
                    grantType = GRANT_TYPE_REFRESH_TOKEN
                )
                val token = traktNetworkService.refreshToken(request)

                traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceAccessTokenKey, token.accessToken)
                traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceRefreshTokenKey, token.refreshToken)
                traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceTokenExpireTimeKey, token.createdAt + token.expiresIn)
                accessToken = token.accessToken
            }

            val user = traktNetworkService.settings(accessToken).user
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceUserIdKey, user.ids.trakt)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceUsernameKey, user.username)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceNameKey, user.name ?: user.username)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceAvatarKey, user.images?.avatar?.full.orEmpty())
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            throw TraktAccountRefreshException(exception.message.orEmpty())
        }
    }

    data class TraktAccountRefreshException(
        override val message: String
    ): Exception(message)

    private companion object {
        private const val REFRESH_THRESHOLD_SECONDS = 86_400L
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }
}
