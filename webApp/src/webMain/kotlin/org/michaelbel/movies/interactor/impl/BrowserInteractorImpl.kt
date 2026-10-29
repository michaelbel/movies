package org.michaelbel.movies.interactor.impl

import kotlinx.browser.window
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.interactor.entity.AuthRedirect

class BrowserInteractorImpl: BrowserInteractor {

    override fun consumeAuthRedirect(): AuthRedirect? {
        val query = window.location.search.removePrefix("?")
        val parameters = query
            .split("&")
            .filter { parameter -> parameter.isNotEmpty() }
            .associate { parameter ->
                parameter.substringBefore("=") to parameter.substringAfter("=", "")
            }

        val requestToken = parameters["request_token"]
        val approved = parameters["approved"]?.toBooleanStrictOrNull()
        if (requestToken == null && approved == null) {
            return null
        }

        window.history.replaceState(
            data = null,
            title = "",
            url = window.location.pathname + window.location.hash
        )

        return AuthRedirect(
            requestToken = requestToken,
            approved = approved
        )
    }
}
