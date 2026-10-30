package org.michaelbel.movies.domain.usecase

import kotlinx.coroutines.CancellationException
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.trakt.TraktAuthInteractor
import org.michaelbel.movies.domain.usecase.TraktSignInUseCase.TraktSignInException
import org.michaelbel.movies.network.TraktNetworkService
import org.michaelbel.movies.network.config.TRAKT_REDIRECT_URI
import org.michaelbel.movies.network.config.isTraktClientIdEmpty
import org.michaelbel.movies.network.config.traktClientId
import org.michaelbel.movies.network.model.TraktTokenRequest
import org.michaelbel.movies.persistence.datastore.TraktPreferences

class TraktSignInUseCase(
    private val traktAuthInteractor: TraktAuthInteractor,
    private val traktNetworkService: TraktNetworkService,
    private val traktPreferences: TraktPreferences,
    dispatchers: SharedDispatchers
): UseCase<Unit, Unit>(dispatchers.io) {

    override suspend fun execute(params: Unit) {
        if (!traktAuthInteractor.isSupported) {
            throw TraktSignInException("Trakt sign in is not supported on this platform")
        }
        if (isTraktClientIdEmpty) {
            throw TraktSignInException("TRAKT_CLIENT_ID is not set")
        }

        try {
            val authResult = traktAuthInteractor.authorize(traktClientId, TRAKT_REDIRECT_URI)

            val tokenRequest = TraktTokenRequest(
                code = authResult.code,
                clientId = traktClientId,
                redirectUri = TRAKT_REDIRECT_URI,
                grantType = GRANT_TYPE_AUTHORIZATION_CODE,
                codeVerifier = authResult.codeVerifier
            )
            val token = traktNetworkService.token(tokenRequest)

            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceAccessTokenKey, token.accessToken)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceRefreshTokenKey, token.refreshToken)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceTokenExpireTimeKey, token.createdAt + token.expiresIn)

            val user = traktNetworkService.settings(token.accessToken).user
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceUserIdKey, user.ids.trakt)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceUsernameKey, user.username)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceNameKey, user.name ?: user.username)
            traktPreferences.setValue(TraktPreferences.PreferenceKey.PreferenceAvatarKey, user.images?.avatar?.full.orEmpty())
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            throw TraktSignInException(exception.message.orEmpty())
        }
    }

    data class TraktSignInException(
        override val message: String
    ): Exception(message)

    private companion object {
        private const val GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code"
    }
}
