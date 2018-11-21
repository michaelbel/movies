package org.michaelbel.moviemade.utils

import androidx.annotation.IntDef
import org.michaelbel.moviemade.utils.Error.Companion.ERROR_AUTH_WITH_LOGIN
import org.michaelbel.moviemade.utils.Error.Companion.ERROR_CONNECTION_NO_TOKEN
import org.michaelbel.moviemade.utils.Error.Companion.ERROR_NO_CONNECTION
import org.michaelbel.moviemade.utils.Error.Companion.ERROR_UNAUTHORIZED

@IntDef(
    ERROR_UNAUTHORIZED,
    ERROR_NO_CONNECTION,
    ERROR_CONNECTION_NO_TOKEN,
    ERROR_AUTH_WITH_LOGIN
)
@Error
annotation class Error {
    companion object {
        const val ERROR_UNAUTHORIZED = 0
        const val ERROR_NO_CONNECTION = 1
        const val ERROR_CONNECTION_NO_TOKEN = 2
        const val ERROR_AUTH_WITH_LOGIN = 3
    }
}