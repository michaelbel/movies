package org.michaelbel.movies.interactor

import org.michaelbel.movies.interactor.entity.AuthRedirect

interface BrowserInteractor {

    /**
     * Address of the running app (origin + pathname), TMDB returns the user here after authorization.
     */
    val authRedirectUrl: String

    /**
     * Reads `request_token` and `approved` query parameters of the current address and removes the whole query
     * from the address bar. Returns `null` when the address contains no TMDB redirect parameters.
     */
    fun consumeAuthRedirect(): AuthRedirect?

    /**
     * Navigates the current tab to [url]. Used for the TMDB authorization page, which returns to [authRedirectUrl].
     */
    fun navigate(url: String)
}
