package org.michaelbel.movies.common.trakt.impl

import org.michaelbel.movies.common.trakt.TraktAuthException
import org.michaelbel.movies.common.trakt.TraktAuthInteractor
import org.michaelbel.movies.common.trakt.TraktAuthResult

class TraktAuthInteractorImpl: TraktAuthInteractor {

    override val isSupported: Boolean = false

    override suspend fun authorize(clientId: String, redirectUri: String): TraktAuthResult {
        throw TraktAuthException("Trakt sign in is not supported on this platform")
    }
}
