package org.michaelbel.movies.interactor

import org.michaelbel.movies.interactor.entity.AuthRedirect

interface BrowserInteractor {

    /**
     * Reads `request_token` and `approved` query parameters of the current address and removes the whole query
     * from the address bar. Returns `null` when the address contains no TMDB redirect parameters.
     */
    fun consumeAuthRedirect(): AuthRedirect?
}
