package org.michaelbel.movies.auth.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.auth_impl_kmp.R
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateRequestTokenException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException

val Throwable?.text: String
    @Composable get() = when (this) {
        is CreateRequestTokenException -> stringResource(R.string.auth_error_while_create_request_token)
        is CreateSessionWithLoginException -> stringResource(R.string.auth_error_while_create_session_with_login)
        is CreateSessionException -> stringResource(R.string.auth_error_while_create_session)
        is AccountDetailsException -> stringResource(R.string.auth_error_while_loading_account_details)
        else -> ""
    }