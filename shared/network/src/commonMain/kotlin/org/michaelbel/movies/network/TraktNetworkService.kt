package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorTraktService
import org.michaelbel.movies.network.model.TraktMovieResponse
import org.michaelbel.movies.network.model.TraktMoviesResult
import org.michaelbel.movies.network.model.TraktRefreshTokenRequest
import org.michaelbel.movies.network.model.TraktRevokeRequest
import org.michaelbel.movies.network.model.TraktSettingsResponse
import org.michaelbel.movies.network.model.TraktTokenRequest
import org.michaelbel.movies.network.model.TraktTokenResponse

class TraktNetworkService(
    private val ktorTraktService: KtorTraktService
) {

    suspend fun movie(traktId: Int): TraktMovieResponse {
        return ktorTraktService.movie(traktId)
    }

    suspend fun search(query: String, page: Int, limit: Int): TraktMoviesResult {
        return ktorTraktService.search(query, page, limit)
    }

    suspend fun movies(list: String, page: Int, limit: Int): TraktMoviesResult {
        return ktorTraktService.movies(list, page, limit)
    }

    suspend fun token(request: TraktTokenRequest): TraktTokenResponse {
        return ktorTraktService.token(request)
    }

    suspend fun refreshToken(request: TraktRefreshTokenRequest): TraktTokenResponse {
        return ktorTraktService.refreshToken(request)
    }

    suspend fun revokeToken(request: TraktRevokeRequest) {
        ktorTraktService.revokeToken(request)
    }

    suspend fun settings(accessToken: String): TraktSettingsResponse {
        return ktorTraktService.settings(accessToken)
    }
}
