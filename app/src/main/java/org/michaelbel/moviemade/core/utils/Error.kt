package org.michaelbel.moviemade.core.utils

import androidx.annotation.IntDef
import org.michaelbel.moviemade.core.utils.Error.Companion.ERROR_AUTH_WITH_LOGIN
import org.michaelbel.moviemade.core.utils.Error.Companion.ERROR_CONNECTION_NO_TOKEN
import org.michaelbel.moviemade.core.utils.Error.Companion.ERROR_NOT_FOUND
import org.michaelbel.moviemade.core.utils.Error.Companion.ERROR_UNAUTHORIZED
import org.michaelbel.moviemade.core.utils.Error.Companion.ERR_NO_CONNECTION
import org.michaelbel.moviemade.core.utils.Error.Companion.ERR_NO_MOVIES

@IntDef(
    ERR_NO_CONNECTION,
    ERR_NO_MOVIES,
    ERROR_UNAUTHORIZED,
    ERROR_CONNECTION_NO_TOKEN,
    ERROR_AUTH_WITH_LOGIN,
    ERROR_NOT_FOUND
)
@Error
annotation class Error {
    companion object {
        const val ERR_NO_CONNECTION = -100
        const val ERR_NO_MOVIES = -101
        const val ERROR_UNAUTHORIZED = 0
        const val ERROR_CONNECTION_NO_TOKEN = 2
        const val ERROR_AUTH_WITH_LOGIN = 3
        const val ERROR_NOT_FOUND = 4
    }
}