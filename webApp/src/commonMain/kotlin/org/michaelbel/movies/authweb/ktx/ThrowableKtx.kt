package org.michaelbel.movies.authweb.ktx

import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase.AccountDetailsException
import org.michaelbel.movies.domain.usecase.CreateRequestTokenUseCase.CreateRequestTokenException
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase.CreateSessionException
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase.CreateSessionWithLoginException

val Throwable?.text: String
    get() = when (this) {
        is CreateRequestTokenException -> "Error while create request token"
        is CreateSessionWithLoginException -> "Invalid username or password"
        is CreateSessionException -> "Error while create session"
        is AccountDetailsException -> "Error while loading account details"
        else -> ""
    }
