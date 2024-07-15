package org.michaelbel.movies.auth.ktx

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateRequestTokenException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.ui.strings.MoviesStrings

val Throwable?.text: String
    @Composable get() = when (this) {
        is CreateRequestTokenException -> stringResource(MoviesStrings.auth_error_while_create_request_token)
        is CreateSessionWithLoginException -> stringResource(MoviesStrings.auth_error_while_create_session_with_login)
        is CreateSessionException -> stringResource(MoviesStrings.auth_error_while_create_session)
        is AccountDetailsException -> stringResource(MoviesStrings.auth_error_while_loading_account_details)
        else -> ""
    }