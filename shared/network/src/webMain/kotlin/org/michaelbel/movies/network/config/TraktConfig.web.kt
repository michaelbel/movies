package org.michaelbel.movies.network.config

import org.michaelbel.movies.network.BuildKonfig

actual val traktClientId: String
    get() = BuildKonfig.TRAKT_CLIENT_ID

actual val isTraktClientIdEmpty: Boolean
    get() = traktClientId.isEmpty() || traktClientId == "null"
